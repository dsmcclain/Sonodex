package dev.dylan.Sonodex.service;

import dev.dylan.Sonodex.entity.Artist;
import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.repository.ArtistRepository;
import dev.dylan.Sonodex.repository.TrackRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
public class TestTrackServiceImpl {
    @Mock
    private TrackRepository trackRepository;

    @Mock
    private ArtistRepository artistRepository;

    @Autowired
    EntityManager entityManager;
    @InjectMocks
    private TrackServiceImpl service;

    private Artist artist = Artist.builder().name("Test Artist").build();
    private Track track = Track.builder().name("Test Track").artists(Set.of(artist)).build();

    @Test
    public void testGetAll() {
        Mockito.when(trackRepository.findAll()).thenReturn(List.of(track));
        List<Track> tracks = service.getAll();

        assertEquals(1, tracks.size());
    }

    @Test
    public void testAddTrack() {
        Mockito.when(trackRepository.save(track)).thenReturn(track);
        Mockito.when(artistRepository.save(artist)).thenReturn(artist);


        Track createdTrack = service.addTrack(track);

        assertEquals("Test Track", createdTrack.getName());
        assertEquals("Test Artist", createdTrack.getArtists().iterator().next().getName());
    }
}
