package com.kagan.flightapidemo.repositories;

import com.kagan.flightapidemo.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    Airport findByCity(String city);
}
