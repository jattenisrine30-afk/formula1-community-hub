package com.f1hub.f1communityhub.controller;

import com.f1hub.f1communityhub.service.F1Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/f1")
@RequiredArgsConstructor
public class F1Controller {

    private final F1Service f1Service;

    @GetMapping(value = "/races", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRaces() {
        String races = f1Service.getCurrentSeasonRaces();
        return ResponseEntity.ok(races);
    }

    @GetMapping(value = "/races/{round}/results", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRaceResults(@PathVariable int round) {
        String results = f1Service.getRaceResults(round);
        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/standings/drivers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getDriverStandings() {
        String standings = f1Service.getDriverStandings();
        return ResponseEntity.ok(standings);
    }
}