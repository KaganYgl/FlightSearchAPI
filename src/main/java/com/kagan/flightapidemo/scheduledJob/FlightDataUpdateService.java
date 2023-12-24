package com.kagan.flightapidemo.scheduledJob;

import com.kagan.flightapidemo.entities.Flight;
import com.kagan.flightapidemo.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FlightDataUpdateService {

    private final FlightService flightService;
    private final RestTemplate restTemplate;

    @Autowired
    public FlightDataUpdateService(FlightService flightService, RestTemplateBuilder restTemplateBuilder) {
        this.flightService = flightService;
        this.restTemplate = restTemplateBuilder.build();
    }

    public void updateFlightData() {
        List<Flight> flightsFromApi = mockApiRequest();
        for (Flight flight: flightsFromApi) {
            flightService.createFlight(flight);
        }
    }

    private List<Flight> mockApiRequest() {
        //this part commented out because this is not a real API to get flight data
        /*
        return restTemplate.exchange(
                "https://www.kagansmockapi.com/api/flights",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Flight>>() {}
                ).getBody();
        */
        return null;
    }
}
