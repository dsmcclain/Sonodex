package dev.dylan.Sonodex.repository;

import dev.dylan.Sonodex.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    @Modifying
    @Query(value = "DELETE FROM Track t where t.id = ?1")
    int customDeleteById(Long id);
}
