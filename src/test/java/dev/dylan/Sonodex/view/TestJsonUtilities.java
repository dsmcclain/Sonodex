package dev.dylan.Sonodex.view;

import dev.dylan.Sonodex.entity.Artist;
import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.entity.TrackMediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestJsonUtilities {

    private static final Track track = Track.builder()
            .id(1L)
            .name("Test Track")
            .albumId(1L)
            .issueDate(LocalDate.of(2024, 1, 1))
            .duration("5:45")
            .trackMediaType(TrackMediaType.OGG)
            .build();
    private static final Artist artist = Artist.builder()
            .id(1L)
            .name("Test Artist")
            .tracks(Set.of(track))
            .build();

    @BeforeAll
    public static void init() {
        track.addArtist(artist);
    }
    @Test
    public void testArtistView() {
       String view = JsonUtilities.ArtistView(artist);
       assertEquals(
"""
                {"id":1,"name":"Test Artist","tracks":""" +
        """
                [{"id":1,"name":"Test Track","albumId":1,"duration":"5:45","trackMediaType":"OGG","issueDate":"2024-01-01"}]}"""
               , view);
    }

    @Test
    public void testArtistViewWithList() {
       Artist artist2 = Artist.builder()
               .id(1L)
               .name("Test Artist 2")
               .tracks(Set.of(track))
               .build();

        String view = JsonUtilities.ArtistView(List.of(artist, artist2));
        assertEquals(
"""
                [{"id":1,"name":"Test Artist","tracks":""" +
        """
                [{"id":1,"name":"Test Track","albumId":1,"duration":"5:45","trackMediaType":"OGG","issueDate":"2024-01-01"}]},\s""" +
        """
                {"id":1,"name":"Test Artist 2","tracks":""" +
        """
                [{"id":1,"name":"Test Track","albumId":1,"duration":"5:45","trackMediaType":"OGG","issueDate":"2024-01-01"}]}]"""
                , view);
    }

    @Test
    public void testTrackView() {
        String view = JsonUtilities.TrackView(track);

        assertEquals("""
                {"id":1,"name":"Test Track","albumId":1,"duration":"5:45","trackMediaType":"OGG","issueDate":"2024-01-01","artists":""" +
        """
                [{"id":1,"name":"Test Artist"}],"price":"12"}"""
                , view);
    }

    @Test
    public void testTrackViewWithList() {
       Track track2 = Track.builder()
               .id(1L)
               .name("Test Track")
               .albumId(1L)
               .issueDate(LocalDate.of(2024, 1, 1))
               .duration("5:45")
               .trackMediaType(TrackMediaType.OGG)
               .build();

       String view = JsonUtilities.TrackView(List.of(track, track2));

       assertEquals("""
               [{"id":1,"name":"Test Track","albumId":1,"duration":"5:45","trackMediaType":"OGG","issueDate":"2024-01-01","artists":""" +
       """
               [{"id":1,"name":"Test Artist"}],"price":"12"},\s""" +
       """
               {"id":1,"name":"Test Track","albumId":1,"duration":"5:45","trackMediaType":"OGG","issueDate":"2024-01-01","artists":[],"price":"12"}]"""
               , view);
    }
}
