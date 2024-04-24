package dev.dylan.Sonodex.service;

import dev.dylan.Sonodex.entity.Track;

import java.util.List;
import java.util.Optional;

public interface TrackService {
    Track addTrack(Track track);

    List<Track> getAll();

    Optional<Track> getTrack(Long id);

    Optional<Track> updateTrack(Long id, Track track);

    boolean deleteTrack(Long id);
}
