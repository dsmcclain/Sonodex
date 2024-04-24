package dev.dylan.Sonodex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonArray;
import dev.dylan.Sonodex.entity.Artist;
import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.view.JsonViewProfiles;
import org.json.JSONArray;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public final class JsonUtilities {
    private JsonUtilities() {};
    private static ObjectMapper configuredMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat( "yyyy-MM-dd"));
        return mapper;
    }

    public static String ArtistView(Artist artist) {
        try {
            return configuredMapper().writerWithView(JsonViewProfiles.Artist.class).writeValueAsString(artist);
        } catch(JsonProcessingException ex) {
           throw new RuntimeException(ex);
        }
    }

    public static String ArtistView(List<Artist> artists) {
       return artists.stream()
                .map(JsonUtilities::ArtistView)
                .toList().toString();
    }

    public static String TrackView(Track track) {
        try {
            return configuredMapper().writerWithView(JsonViewProfiles.Track.class).writeValueAsString(track);
        } catch(JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String TrackView(List<Track> tracks) {
        return tracks.stream()
                .map(JsonUtilities::TrackView)
                .toList().toString();
    }
}
