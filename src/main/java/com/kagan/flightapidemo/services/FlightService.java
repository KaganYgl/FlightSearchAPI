package com.kagan.flightapidemo.services;

import com.kagan.flightapidemo.entities.Airport;
import com.kagan.flightapidemo.entities.Flight;
import com.kagan.flightapidemo.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportService airportService;

    @Autowired
    public FlightService(FlightRepository flightRepository, AirportService airportService) {
        this.flightRepository = flightRepository;
        this.airportService = airportService;
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElse(null);
    }

    public List<Flight> getFlightsByParameters(
            String departureAirportCity, String arrivalAirportCity,
            LocalDateTime departureDateTime, LocalDateTime returnDateTime) {

        Airport departureAirport = airportService.getAirportByCity(departureAirportCity);
        Airport arrivalAirport = airportService.getAirportByCity(arrivalAirportCity);

        if (departureAirport != null && arrivalAirport != null &&
                departureDateTime != null && returnDateTime != null) {

            List<Flight> departureFlights = flightRepository.findByParameters(
                    departureAirport, arrivalAirport, departureDateTime, returnDateTime);

            List<Flight> returnFlights = flightRepository.findByParameters(
                    arrivalAirport, departureAirport,returnDateTime , null);
            return Stream.concat(departureFlights.stream(), returnFlights.stream()).collect(Collectors.toList());
        } else if (departureAirport != null && arrivalAirport != null &&
                departureDateTime != null) {
            return flightRepository.findByParameters(
                    departureAirport, arrivalAirport, departureDateTime, null);
        } else {
            return flightRepository.findAll();
        }
    }

    public Flight createFlight(Flight flight) {
        Airport departureAirport = flight.getDepartureAirport();
        Airport arrivalAirport = flight.getArrivalAirport();

        Airport existingDepartureAirport = airportService.getAirportByCity(departureAirport.getCity());
        Airport existingArrivalAirport = airportService.getAirportByCity(arrivalAirport.getCity());

        if (existingDepartureAirport == null) {
            existingDepartureAirport = airportService.saveAirport(flight.getDepartureAirport());
        }
        if(existingArrivalAirport == null){
            existingArrivalAirport = airportService.saveAirport(flight.getArrivalAirport());
        }

        flight.setDepartureAirport(existingDepartureAirport);
        flight.setArrivalAirport(existingArrivalAirport);
        return flightRepository.save(flight);
    }

    public Flight updateFlight(Long id, Flight updatedFlight) {
        Flight existingFlight = flightRepository.findById(id).orElse(null);

        if (existingFlight != null) {
            existingFlight.setDepartureAirport(updatedFlight.getDepartureAirport());
            existingFlight.setArrivalAirport(updatedFlight.getArrivalAirport());
            existingFlight.setDepartureDateTime(updatedFlight.getDepartureDateTime());
            existingFlight.setReturnDateTime(updatedFlight.getReturnDateTime());
            existingFlight.setPrice(updatedFlight.getPrice());

            return flightRepository.save(existingFlight);
        } else {
            return null;
        }
    }

    public boolean deleteFlight(Long id) {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
