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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class TestTrackController {
    @Mock
    TrackService trackService;

    @InjectMocks
    private TrackController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testAddTrack() {
        Track track = Track.builder().name("test track").build();
        Mockito.when(trackService.addTrack(track)).thenReturn(track);

        ResponseEntity<?> response = controller.addTrack(track);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Track returnTrack = (Track) response.getBody();
        assert returnTrack != null;
        assertEquals("test track", returnTrack.getName());

    }

    @Test
    public void testGetAll() throws Exception {
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
    public void testAddTrackWithArtists() {

    }
}
