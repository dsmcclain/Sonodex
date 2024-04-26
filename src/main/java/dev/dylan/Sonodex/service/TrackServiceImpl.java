package dev.dylan.Sonodex.service;

import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.entity.TrackMediaType;
import dev.dylan.Sonodex.repository.ArtistRepository;
import dev.dylan.Sonodex.repository.TrackRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TrackServiceImpl implements TrackService {
    private TrackRepository trackRepository;
    private ArtistRepository artistRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository, ArtistRepository artistRepository) {
        this.trackRepository = trackRepository;
        this.artistRepository = artistRepository;
    }

    @Transactional
    @Override
    public Track addTrack(Track track) {
        Track newTrack = trackRepository.save(track);
        artistRepository.saveAll(track.getArtists());
        return newTrack;
    }

    @Override
    public List<Track> getAll() {
        return new ArrayList<>(trackRepository.findAll());
    }

    @Override
    public List<Track> getTracksByMediaType(TrackMediaType type) {
       return new ArrayList<>(trackRepository.findByTrackMediaType(type));
    }

    @Override
    public List<Track> getTracksByYear(int year) {
        LocalDate beginDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        return new ArrayList<>(trackRepository.findAllByIssueDateBetween(beginDate, endDate));
    }

    @Override
    public List<Track> getTracksByArtistId(Long id) {
        return new ArrayList<>(trackRepository.findByArtists_Id(id));
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
