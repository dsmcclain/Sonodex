package dev.dylan.Sonodex.repository;

import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.entity.TrackMediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    //customized to return the deleted record versus void
    @Modifying
    @Query(value = "DELETE FROM Track t where t.id = ?1")
    int customDeleteById(Long id);

    List<Track> findByTrackMediaType(TrackMediaType type);
}
