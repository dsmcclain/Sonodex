package dev.dylan.Sonodex.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.dylan.Sonodex.dto.ArtistDTO;
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
    public void testGetAll() {
        Artist artist2 = Artist.builder().name("test artist 2").build();

        Mockito.when(artistService.getAll()).thenReturn(List.of(artist, artist2));

        ResponseEntity<?> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Artist>returnArtists = (List<Artist>) response.getBody();
        assert returnArtists != null;
        assertEquals(returnArtists.size(), 2);
        assertEquals("test artist 2", returnArtists.getLast().getName());
    }

    @Test
    public void testGetArtistById() throws JsonProcessingException {
        Mockito.when(artistService.getArtist(1L)).thenReturn(Optional.ofNullable(artist));

        ResponseEntity<?> response = controller.getArtist(1L);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        ArtistDTO returnArtist = (ArtistDTO) response.getBody();
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
    public void testAddArtist() {
        Mockito.when(artistService.addArtist(artist)).thenReturn(artist);

        ResponseEntity<?> response = controller.addArtist(artist);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Artist returnArtist = (Artist) response.getBody();
        assert returnArtist != null;
        assertEquals("test artist", returnArtist.getName());
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
