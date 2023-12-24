package com.kagan.flightapidemo.controllers;

import com.kagan.flightapidemo.entities.Flight;
import com.kagan.flightapidemo.services.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @Operation(summary = "Get all flights", description = "Get a list of all flights")
    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @Operation(summary = "Get flight by ID", description = "Get detailed information about a flight based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);

        if (flight != null) {
            return new ResponseEntity<>(flight, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Search flights by parameters", description = "Search for flights based on specified parameters")
    @GetMapping("/search")
    public ResponseEntity<List<Flight>> getFlightsByParameters(
            @RequestParam() String departureAirportCity,
            @RequestParam() String arrivalAirportCity,
            @RequestParam() LocalDateTime departureDateTime,
            @RequestParam(required = false) LocalDateTime returnDateTime
    ) {
        List<Flight> flights = flightService.getFlightsByParameters(
                departureAirportCity, arrivalAirportCity, departureDateTime, returnDateTime);

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @Operation(summary = "Create a new flight", description = "Create a new flight with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Flight created successfully")
    })
    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        Flight createdFlight = flightService.createFlight(flight);
        return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
    }

    @Operation(summary = "Update flight by ID", description = "Update an existing flight based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        Flight updatedFlight = flightService.updateFlight(id, flight);

        if (updatedFlight != null) {
            return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete flight by ID", description = "Delete an existing flight based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Flight deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        boolean deleted = flightService.deleteFlight(id);

        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}