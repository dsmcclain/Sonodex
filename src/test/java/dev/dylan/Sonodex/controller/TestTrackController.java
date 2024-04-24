package dev.dylan.Sonodex.controller;

import dev.dylan.Sonodex.dto.TrackDTO;
import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.service.TrackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestTrackController {
    @Mock
    TrackService trackService;

    @InjectMocks
    private TrackController controller;

    Track track = Track.builder().name("test track").build();

    @Test
    public void testGetAll() {
        Track track2 = Track.builder().name("test track 2").build();

        Mockito.when(trackService.getAll()).thenReturn(List.of(track, track2));

        ResponseEntity<?> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Track> returnTracks = (List<Track>) response.getBody();
        assert returnTracks != null;
        assertEquals(returnTracks.size(), 2);
        assertEquals("test track 2", returnTracks.getLast().getName());
    }

    @Test
    public void testGetTrackById() {
        Mockito.when(trackService.getTrack(1L)).thenReturn(Optional.ofNullable(track));

        ResponseEntity<?> response = controller.getTrack(1L);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        TrackDTO returnTrack = (TrackDTO) response.getBody();
        assert returnTrack != null;
        assertEquals("test track", returnTrack.getName());
    }

    @Test
    public void testGetTrackWithBadId() {
        Mockito.when(trackService.getTrack(1L)).thenReturn(Optional.empty());
        ResponseEntity<?> response = controller.getTrack(1L);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testAddTrack() {
        Mockito.when(trackService.addTrack(track)).thenReturn(track);

        ResponseEntity<?> response = controller.addTrack(track);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Track returnTrack = (Track) response.getBody();
        assert returnTrack != null;
        assertEquals("test track", returnTrack.getName());
    }

    @Test
    public void testUpdateTrack() {
        Track updatedTrack = Track.builder().name("an updated track").build();
        Mockito.when(trackService.updateTrack(1L, updatedTrack)).thenReturn(Optional.ofNullable(updatedTrack));

        ResponseEntity<?> response = controller.updateTrack(1L, updatedTrack);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Track returnTrack = (Track) response.getBody();
        assert returnTrack != null;
        assertEquals(returnTrack.getName(), "an updated track");
    }

    @Test
    public void testDeleteTrack() {
        Mockito.when(trackService.deleteTrack(1L)).thenReturn(true);

        ResponseEntity<?> response = controller.deleteTrack(1L);
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void testDeleteTrackWithBadId() {
        Mockito.when(trackService.deleteTrack(1L)).thenReturn(false);

        ResponseEntity<?> response = controller.deleteTrack(1L);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
