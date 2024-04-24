package dev.dylan.Sonodex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dylan.Sonodex.entity.Artist;
import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.view.JsonViewProfiles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.google.gson.Gson;

import java.util.List;
import java.util.stream.Collectors;

public final class SonodexUtility {
    private SonodexUtility(){}

    public static String ArtistView(Artist artist) {
        try {
            return new ObjectMapper().writerWithView(JsonViewProfiles.Artist.class).writeValueAsString(artist);
        } catch(JsonProcessingException ex) {
           throw new RuntimeException(ex);
        }
    }

    public static List<String> ArtistView(List<Artist> artists) {
       return artists.stream()
                .map(SonodexUtility::ArtistView)
                .collect(Collectors.toList());
    }

    public static String TrackView(Track track) {
        try {
            return new ObjectMapper().writerWithView(JsonViewProfiles.Track.class).writeValueAsString(track);
        } catch(JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static List<String> TrackView(List<Track> tracks) {
        return tracks.stream()
                .map(SonodexUtility::TrackView)
                .collect(Collectors.toList());
    }
}
