package dev.dylan.Sonodex.controller;

import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/tracks")
public class TrackController {
    private final TrackService trackService;

    @Autowired

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping("/tracks")
    public ResponseEntity<Track> addTrack(@RequestBody Track track) {
        Track newTrack = trackService.addTrack(track);

        return ResponseEntity.ok(newTrack);
    }
}
