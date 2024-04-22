package dev.dylan.Sonodex.controller;

import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.repository.TrackRepository;
import dev.dylan.Sonodex.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackController {
    @Autowired
    private TrackService trackService;

    @PostMapping("/tracks")
    public Track saveTrack(@RequestBody Track track) {
        return trackService.saveTrack(track);
    }
}
