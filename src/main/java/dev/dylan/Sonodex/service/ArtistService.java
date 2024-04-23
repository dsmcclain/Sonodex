package dev.dylan.Sonodex.service;

import dev.dylan.Sonodex.entity.Artist;
import java.util.List;
import java.util.Optional;

public interface ArtistService {
    Artist addArtist(Artist artist);

    List<Artist> getAll();

    Optional<Artist> getArtist(Long id);

    boolean deleteArtist(Long id);
}
