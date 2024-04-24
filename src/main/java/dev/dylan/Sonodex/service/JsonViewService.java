package dev.dylan.Sonodex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dylan.Sonodex.entity.Artist;
import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.view.JsonViewProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public final class JsonViewService {
    @Autowired
    ObjectMapper objectMapper;

    public String ArtistView(Artist artist) {
        try {
            return objectMapper.writerWithView(JsonViewProfiles.Artist.class).writeValueAsString(artist);
        } catch(JsonProcessingException ex) {
           throw new RuntimeException(ex);
        }
    }

    public List<String> ArtistView(List<Artist> artists) {
       return artists.stream()
                .map(this::ArtistView)
                .collect(Collectors.toList());
    }

    public String TrackView(Track track) {
        try {
            return objectMapper.writerWithView(JsonViewProfiles.Track.class).writeValueAsString(track);
        } catch(JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<String> TrackView(List<Track> tracks) {
        return tracks.stream()
                .map(this::TrackView)
                .collect(Collectors.toList());
    }
}
