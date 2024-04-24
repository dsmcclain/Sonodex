package dev.dylan.Sonodex.service;

import dev.dylan.Sonodex.entity.Artist;
import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.repository.ArtistRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository) { this.artistRepository = artistRepository; }

    @Transactional
    @Override
    public Artist addArtist(Artist artist) {
        entityManager.persist(artist);
        for(Track track : artist.getTracks()) {
            entityManager.persist(track);
            artist.addTrack(track);
        }
        return artist;
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
    @Transactional
    public Optional<Artist> updateArtist(Long id, Artist artist) {
       Optional<Artist> oldArtist = getArtist(id);
       if(oldArtist.isEmpty())
           return oldArtist;
       else {
           artist.setId(id);
           artist.setTracks(oldArtist.get().getTracks());
           entityManager.merge(artist);
           return Optional.of(artist);

       }
    }
    @Override
    public boolean deleteArtist(Long id) {
        int numberOfRecordsDeleted = artistRepository.customDeleteById(id);
        return numberOfRecordsDeleted == 1;
    }
}
