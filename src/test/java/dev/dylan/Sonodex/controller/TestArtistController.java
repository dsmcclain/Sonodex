package dev.dylan.Sonodex.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dylan.Sonodex.entity.Artist;
import dev.dylan.Sonodex.service.ArtistService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestArtistController {
    @Mock
    ArtistService artistService;

    @InjectMocks
    private ArtistController controller;

    Artist artist = Artist.builder().name("test artist").build();

    @Test
    public void testGetAll() throws JsonProcessingException {
        Artist artist2 = Artist.builder().name("test artist 2").build();

        Mockito.when(artistService.getAll()).thenReturn(List.of(artist, artist2));

        ResponseEntity<?> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List artistResponse = new ObjectMapper().readValue((String) response.getBody(), List.class);

        assertEquals(2, artistResponse.size());
    }

    @Test
    public void testGetArtistById() throws IOException {
        Mockito.when(artistService.getArtist(1L)).thenReturn(Optional.ofNullable(artist));

        ResponseEntity<?> response = controller.getArtist(1L);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        Artist returnArtist = new ObjectMapper().reader().readValue((String) response.getBody(), Artist.class);
        assert returnArtist != null;

        assertEquals("test artist", returnArtist.getName());
    }

    @Test
    public void testGetArtistWithBadId() throws JsonProcessingException {
        Mockito.when(artistService.getArtist(1L)).thenReturn(Optional.empty());
        ResponseEntity<?> response = controller.getArtist(1L);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testAddArtist() throws IOException {
        Mockito.when(artistService.addArtist(artist)).thenReturn(artist);

        ResponseEntity<?> response = controller.addArtist(artist);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Artist returnArtist = new ObjectMapper().reader().readValue((String) response.getBody(), Artist.class);
        assert returnArtist != null;

        assertEquals("test artist", returnArtist.getName());
    }

    @Test
    public void testUpdateArtist() throws IOException {
        Artist updatedArtist = Artist.builder().name("an updated artist").build();
        Mockito.when(artistService.updateArtist(1L, updatedArtist)).thenReturn(Optional.ofNullable(updatedArtist));

        ResponseEntity<?> response = controller.updateArtist(1L, updatedArtist);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        HashMap returnTrack = new ObjectMapper().readValue((response.getBody()).toString(), HashMap.class);
        assertEquals(returnTrack.get("name"), "an updated artist");
    }

    @Test
    public void testDeleteArtist() {
        Mockito.when(artistService.deleteArtist(1L)).thenReturn(true);

        ResponseEntity<?> response = controller.deleteArtist(1L);
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void testDeleteArtistWithBadId() {
        Mockito.when(artistService.deleteArtist(1L)).thenReturn(false);

        ResponseEntity<?> response = controller.deleteArtist(1L);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
