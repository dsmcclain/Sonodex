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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
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
    public void testGetAll() throws IOException {
        Track track2 = Track.builder().name("test track 2").build();

        Mockito.when(trackService.getAll()).thenReturn(List.of(track, track2));

        ResponseEntity<?> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List trackResponse = new ObjectMapper().readValue((String) response.getBody(), List.class);
        assertEquals(2, trackResponse.size());
    }

    @Test
    public void testGetTrackById() throws IOException {
        Mockito.when(trackService.getTrack(1L)).thenReturn(Optional.ofNullable(track));

        ResponseEntity<?> response = controller.getTrack(1L);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        Track returnTrack = new ObjectMapper().reader().readValue((String) response.getBody(), Track.class);
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
    public void testAddTrack() throws IOException {
        Mockito.when(trackService.addTrack(track)).thenReturn(track);

        ResponseEntity<?> response = controller.addTrack(track);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Track returnTrack = new ObjectMapper().reader().readValue((String) response.getBody(), Track.class);
        assert returnTrack != null;

        assertEquals("test track", returnTrack.getName());
    }

    @Test
    public void testUpdateTrack() throws IOException {
        Track updatedTrack = Track.builder().name("an updated track").build();
        Mockito.when(trackService.updateTrack(1L, updatedTrack)).thenReturn(Optional.ofNullable(updatedTrack));

        ResponseEntity<?> response = controller.updateTrack(1L, updatedTrack);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Track returnTrack = new ObjectMapper().reader().readValue((String) response.getBody(), Track.class);
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
