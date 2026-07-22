package com.f1hub.f1communityhub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class F1Service {

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://api.jolpi.ca/ergast/f1";
    private static final String SEASON = "2026";

    @Cacheable(value = "races", key = "'current-season'")
    public String getCurrentSeasonRaces() {
        String url = BASE_URL + "/" + SEASON + "/races";
        return restTemplate.getForObject(url, String.class);
    }

    @Cacheable(value = "raceResults", key = "#round")
    public String getRaceResults(int round) {
        String url = BASE_URL + "/" + SEASON + "/" + round + "/results";
        return restTemplate.getForObject(url, String.class);
    }

    @Cacheable(value = "driverStandings", key = "'current-standings'")
    public String getDriverStandings() {
        String url = BASE_URL + "/" + SEASON + "/driverstandings";
        return restTemplate.getForObject(url, String.class);
    }
}