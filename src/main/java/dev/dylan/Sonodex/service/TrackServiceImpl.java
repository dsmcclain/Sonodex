package dev.dylan.Sonodex.service;

import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackServiceImpl implements TrackService {
    private final TrackRepository trackRepository;
    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }
    @Override
    public Track addTrack(Track track) {
        return trackRepository.save(track);
    }
}
