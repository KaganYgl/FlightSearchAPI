package com.kagan.flightapidemo.services;

import com.kagan.flightapidemo.entities.Airport;
import com.kagan.flightapidemo.repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    public Optional<Airport> getAirportById(Long id) {
        return airportRepository.findById(id);
    }

    public Airport getAirportByCity(String city) {
        return airportRepository.findByCity(city);
    }

    public Airport saveAirport(Airport airport) {
        Airport existingAirport = airportRepository.findByCity(airport.getCity());

        if (existingAirport != null) {
            return existingAirport;
        } else {
            return airportRepository.save(airport);
        }
    }

    public void deleteAirport(Long id) {
        airportRepository.deleteById(id);
    }
}
