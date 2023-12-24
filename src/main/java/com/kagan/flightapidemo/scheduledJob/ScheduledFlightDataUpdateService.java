package com.kagan.flightapidemo.scheduledJob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledFlightDataUpdateService {

    private final FlightDataUpdateService flightDataUpdateService;

    @Autowired
    public ScheduledFlightDataUpdateService(FlightDataUpdateService flightDataUpdateService) {
        this.flightDataUpdateService = flightDataUpdateService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateFlightsScheduledJob() {
        flightDataUpdateService.updateFlightData();
    }
}

