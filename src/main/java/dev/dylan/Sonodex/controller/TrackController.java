package dev.dylan.Sonodex.controller;

import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.entity.TrackMediaType;
import dev.dylan.Sonodex.service.TrackService;
import dev.dylan.Sonodex.view.JsonUtilities;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/tracks", produces = "application/json")
public class TrackController {
    private TrackService trackService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getPackageName());

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping
    public ResponseEntity<String> getAll() {
        return ResponseEntity.ok(JsonUtilities.TrackView(trackService.getAll()));
    }

    @GetMapping("media-type/{mediaType}")
    public ResponseEntity<String> getTracksByMediaType(@PathVariable("mediaType") TrackMediaType type) {
        return ResponseEntity.ok(JsonUtilities.TrackView(trackService.getTracksByMediaType(type)));
    }

    @GetMapping("/tracks-by-artist/{id}")
    public ResponseEntity<String> getTracksByArtistId(@PathVariable("id") Long id) {
        Optional<Track> trackLookup = trackService.getTrack(id);

        return ResponseEntity.ok(JsonUtilities.TrackView(trackService.getTracksByArtistId(id)));
    }

    @GetMapping("/tracks-by-year/{year}")
    public ResponseEntity<String> getTracksByYear(@PathVariable("year") int year) {
        return ResponseEntity.ok(JsonUtilities.TrackView(trackService.getTracksByYear(year)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrack(@PathVariable("id") Long id) {
        Optional<Track> trackResponse = trackService.getTrack(id);
        return trackResponse.<ResponseEntity<?>>map(
                track -> ResponseEntity.ok(JsonUtilities.TrackView(track))
        ).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("No track found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<String> addTrack(@Valid @RequestBody Track track) {
        Track newTrack = trackService.addTrack(track);
        logger.info("new track created: " + newTrack.toString());

        return ResponseEntity.ok(JsonUtilities.TrackView(newTrack));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrack(@PathVariable("id") Long id, @Valid @RequestBody Track track) {
        Optional<Track> trackResponse = trackService.updateTrack(id, track);
        return trackResponse.<ResponseEntity<?>>map(
                value -> ResponseEntity.ok(JsonUtilities.TrackView(value))
        ).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("No track found with id: " + id));
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
