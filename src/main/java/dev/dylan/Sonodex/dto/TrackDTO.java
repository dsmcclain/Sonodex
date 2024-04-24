package dev.dylan.Sonodex.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.dylan.Sonodex.entity.Artist;
import dev.dylan.Sonodex.entity.Track;
import dev.dylan.Sonodex.entity.TrackMediaType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
public class TrackDTO {
    // THIS CLASS WAS ORIGINALLY INTENDED AS A SOLUTION TO RENDERING ARTISTS WITHOUT
    // CREATING AN INFINITE LOOP OF TRACK-ARTISTS-TRACKS ASSOCIATIONS.
    // ABANDONED IN FAVOR OF THE JSONVIEW APPROACH. LEAVING THIS HERE AS INSTRUCTIONAL CODE.
    private Long id;
    private String name;
    private Long albumId;
    private LocalDate issueDate;
    private String duration;
    private TrackMediaType trackMediaType;
    private ArrayList<ArtistDTO> artists = new ArrayList<>();

    public TrackDTO() {
    }

    public TrackDTO(Track track) {
        this.id = track.getId();
        this.name = track.getName();
        this.albumId = track.getAlbumId();
        this.issueDate = track.getIssueDate();
        this.duration = track.getDuration();
        this.trackMediaType = track.getTrackMediaType();
        for(Artist artist : track.getArtists()) {
            ArtistDTO artistDTO = new ArtistDTO();
            artistDTO.setId(artist.getId());
            artistDTO.setName(artist.getName());
            artists.add(artistDTO);
        }
    }
}
