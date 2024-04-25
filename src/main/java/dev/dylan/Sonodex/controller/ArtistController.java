package dev.dylan.Sonodex.controller;

import dev.dylan.Sonodex.JsonUtilities;
import dev.dylan.Sonodex.entity.Artist;
import dev.dylan.Sonodex.service.ArtistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value =  "/artists", produces = "application/json")
public class ArtistController {
    private ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<String> getAll() {

        return ResponseEntity.ok(JsonUtilities.ArtistView(artistService.getAll()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getArtist(@PathVariable("id") Long id) {
        Optional<Artist> artistResponse = artistService.getArtist(id);
        return artistResponse.<ResponseEntity<?>>map(
                artist -> ResponseEntity.ok(JsonUtilities.ArtistView(artist))
        ).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("No artist found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<String> addArtist(@Valid @RequestBody Artist artist) {
        Artist newArtist = artistService.addArtist(artist);

        return ResponseEntity.ok(JsonUtilities.ArtistView(newArtist));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArtist(@PathVariable("id") Long id, @Valid @RequestBody Artist artist) {
        Optional<Artist> artistResponse = artistService.updateArtist(id, artist);
        return artistResponse.<ResponseEntity<?>>map(
                value -> ResponseEntity.ok(JsonUtilities.ArtistView(artist))
        ).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("No artist found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable("id") Long id) {
        boolean result = artistService.deleteArtist(id);

        if(!result)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No artist found with id: " + id);
        else
            return ResponseEntity.noContent().build();
    }
}
