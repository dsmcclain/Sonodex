package dev.dylan.Sonodex.repository;

import dev.dylan.Sonodex.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    //customized to return the deleted record versus void
    @Modifying
    @Query(value = "DELETE FROM Artist a where a.id = ?1")
    int customDeleteById(Long id);
}