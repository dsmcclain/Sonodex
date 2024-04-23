package dev.dylan.Sonodex.controller;

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

    @GetMapping
    public ResponseEntity<List<Artist>> getAll() {
        return ResponseEntity.ok(artistService.getAll());
    }

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArtist(@PathVariable("id") Long id) {
        Optional<Artist> artistResponse = artistService.getArtist(id);
        if(artistResponse.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No artist found with id: " + id);
        else
            return ResponseEntity.ok(artistResponse.get());
    }

    @PostMapping
    public ResponseEntity<Artist> addArtist(@Valid @RequestBody Artist artist) {
        Artist newArtist = artistService.addArtist(artist);

        return ResponseEntity.ok(newArtist);
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
