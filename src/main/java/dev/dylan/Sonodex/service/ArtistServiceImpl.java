package dev.dylan.Sonodex.service;

import dev.dylan.Sonodex.entity.Artist;
import dev.dylan.Sonodex.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository) { this.artistRepository = artistRepository; }

    @Override
    public Artist addArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    @Override
    public List<Artist> getAll() {
        return new ArrayList<>(artistRepository.findAll());
    }

    @Override
    public Optional<Artist> getArtist(Long id) {
        return artistRepository.findById(id);
    }

    @Override
    public boolean deleteArtist(Long id) {
        int numberOfRecordsDeleted = artistRepository.customDeleteById(id);
        return numberOfRecordsDeleted == 1;
    }
}
