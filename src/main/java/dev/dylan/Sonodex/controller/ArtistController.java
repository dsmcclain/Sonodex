package dev.dylan.Sonodex.controller;

import dev.dylan.Sonodex.SonodexUtility;
import dev.dylan.Sonodex.entity.Artist;
import dev.dylan.Sonodex.service.ArtistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
public class ArtistController {
    private ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getAll() {

        return ResponseEntity.ok(SonodexUtility.ArtistView(artistService.getAll()));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getArtist(@PathVariable("id") Long id) {
        Optional<Artist> artistResponse = artistService.getArtist(id);
        return artistResponse.<ResponseEntity<?>>map(
                artist -> ResponseEntity.ok(SonodexUtility.ArtistView(artist))
        ).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("No artist found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<String> addArtist(@Valid @RequestBody Artist artist) {
        Artist newArtist = artistService.addArtist(artist);

        return ResponseEntity.ok(SonodexUtility.ArtistView(newArtist));
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
