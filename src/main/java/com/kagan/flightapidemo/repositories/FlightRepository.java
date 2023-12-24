package com.kagan.flightapidemo.repositories;

import com.kagan.flightapidemo.entities.Airport;
import com.kagan.flightapidemo.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT f FROM Flight f WHERE " +
            "f.departureAirport = :departureAirport AND " +
            "f.arrivalAirport = :arrivalAirport AND " +
            "f.departureDateTime = :departureDateTime AND " +
            "(COALESCE(:returnDateTime, f.returnDateTime) IS NULL OR f.returnDateTime = :returnDateTime)")
    List<Flight> findByParameters(
            @Param("departureAirport") Airport departureAirport,
            @Param("arrivalAirport") Airport arrivalAirport,
            @Param("departureDateTime") LocalDateTime departureDateTime,
            @Param("returnDateTime") LocalDateTime returnDateTime);
}
