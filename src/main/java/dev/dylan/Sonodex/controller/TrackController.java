package dev.dylan.Sonodex.controller;

import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/tracks")
public class TrackController {
    private TrackService trackService;

    @Autowired

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping("/tracks")
    public ResponseEntity<Track> addTrack(@RequestBody Track track) {
        Track newTrack = trackService.addTrack(track);

        return ResponseEntity.ok(newTrack);
    }

    @GetMapping("/tracks")
    public ResponseEntity<List<Track>> getAll() {
        return ResponseEntity.ok(trackService.getAll());
    }

    @GetMapping("/tracks/{id}")
    public ResponseEntity<?> getTrack(@PathVariable("id") Long id) {
        Optional<Track> track = trackService.getTrack(id);
        if(track.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No track found with id: " + id);
        else
            return ResponseEntity.ok(track);
    }
}
