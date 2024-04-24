package dev.dylan.Sonodex.service;

import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.repository.TrackRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrackServiceImpl implements TrackService {
    private final TrackRepository trackRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }
    @Override
    public Track addTrack(Track track) {
        return trackRepository.save(track);
    }

    @Override
    public List<Track> getAll() {
        return new ArrayList<>(trackRepository.findAll());
    }

    @Override
    public Optional<Track> getTrack(Long id) {
        return trackRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Track> updateTrack(Long id, Track track) {
        Optional<Track> oldTrack = getTrack(id);
        if(oldTrack.isEmpty())
            return oldTrack;
        else {
            track.setId(id);
            track.setArtists(oldTrack.get().getArtists());
            entityManager.merge(track);
            return Optional.of(track);
        }
    }
    @Override
    public boolean deleteTrack(Long id) {
        int numberOfRecordsDeleted = trackRepository.customDeleteById(id);
        return numberOfRecordsDeleted == 1;
    }
}
