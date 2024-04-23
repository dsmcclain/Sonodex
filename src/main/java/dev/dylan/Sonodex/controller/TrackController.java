package dev.dylan.Sonodex.controller;

import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.service.TrackService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tracks")
public class TrackController {
    private TrackService trackService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getPackageName());

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping
    public ResponseEntity<List<Track>> getAll() {
        return ResponseEntity.ok(trackService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrack(@PathVariable("id") Long id) {
        Optional<Track> trackResponse = trackService.getTrack(id);
        if(trackResponse.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No track found with id: " + id);
        else
            return ResponseEntity.ok(trackResponse.get());
    }

    @PostMapping
    public ResponseEntity<Track> addTrack(@Valid @RequestBody Track track) {
        Track newTrack = trackService.addTrack(track);
        logger.info("new track created: " + newTrack.toString());

        return ResponseEntity.ok(newTrack);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable("id") Long id) {
        boolean result = trackService.deleteTrack(id);

        if(!result)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No track found with id: " + id);
        else
            return ResponseEntity.noContent().build();
    }
}
