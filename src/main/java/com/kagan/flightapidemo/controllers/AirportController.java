package com.kagan.flightapidemo.controllers;

import com.kagan.flightapidemo.entities.Airport;
import com.kagan.flightapidemo.services.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @Operation(summary = "Get all airports", description = "Get a list of all airports")
    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports() {
        List<Airport> airports = airportService.getAllAirports();
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }

    @Operation(summary = "Get airport by ID", description = "Get detailed information about an airport based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Airport not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
        return airportService.getAirportById(id)
                .map(airport -> new ResponseEntity<>(airport, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Create a new airport", description = "Create a new airport with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Airport created successfully")
    })
    @PostMapping
    public ResponseEntity<Airport> createAirport(@RequestBody Airport airport) {
        Airport savedAirport = airportService.saveAirport(airport);
        return new ResponseEntity<>(savedAirport, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete airport by ID", description = "Delete an airport based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Airport deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Airport not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        airportService.deleteAirport(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
