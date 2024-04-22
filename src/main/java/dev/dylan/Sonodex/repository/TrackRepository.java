package dev.dylan.Sonodex.repository;

import dev.dylan.Sonodex.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {
}
