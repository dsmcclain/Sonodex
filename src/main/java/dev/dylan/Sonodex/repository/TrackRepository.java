package dev.dylan.Sonodex.repository;

import dev.dylan.Sonodex.entity.Track;
import org.springframework.data.repository.CrudRepository;

public interface TrackRepository extends CrudRepository<Track, Long> {
}
