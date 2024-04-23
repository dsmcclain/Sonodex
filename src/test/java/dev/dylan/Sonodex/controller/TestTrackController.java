package dev.dylan.Sonodex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.service.TrackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class TestTrackController {
    @Mock
    TrackService trackService;

    @InjectMocks
    private TrackController controller;

    @Test
    public void testGetAll() {
        Track track1 = Track.builder().name("test track").build();
        Track track2 = Track.builder().name("test track 2").build();

        Mockito.when(trackService.getAll()).thenReturn(List.of(track1, track2));

        ResponseEntity<?> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Track> returnTracks = (List<Track>) response.getBody();
        assert returnTracks != null;
        assertEquals(returnTracks.size(), 2);
        assertEquals("test track 2", returnTracks.getLast().getName());
    }

    @Test
    public void testGetTrackById() {
        Track track = Track.builder().name("test track").build();
        Mockito.when(trackService.getTrack(1L)).thenReturn(Optional.ofNullable(track));

        ResponseEntity<?> response = controller.getTrack(1L);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        Track returnTrack = (Track) response.getBody();
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
        Track track = Track.builder().name("test track").build();
        Mockito.when(trackService.addTrack(track)).thenReturn(track);

        ResponseEntity<?> response = controller.addTrack(track);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Track returnTrack = (Track) response.getBody();
        assert returnTrack != null;
        assertEquals("test track", returnTrack.getName());
    }

    @Test
    public void testDeleteTrack() {
        Track.builder().name("test track").build();
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
