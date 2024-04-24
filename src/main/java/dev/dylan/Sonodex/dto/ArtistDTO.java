package dev.dylan.Sonodex.dto;

import dev.dylan.Sonodex.entity.Artist;
import dev.dylan.Sonodex.entity.Track;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ArtistDTO {
    // THIS CLASS WAS ORIGINALLY INTENDED AS A SOLUTION TO RENDERING ARTISTS WITHOUT
    // CREATING AN INFINITE LOOP OF ARTIST-TRACKS-ARTISTS ASSOCIATIONS.
    // ABANDONED IN FAVOR OF THE JSONVIEW APPROACH. LEAVING THIS HERE AS INSTRUCTIONAL CODE.
    private Long id;
    private String name;
    private ArrayList<TrackDTO> tracks = new ArrayList<>();


    public ArtistDTO() {}

    public ArtistDTO(Artist artist) {
        this.id = artist.getId();
        this.name = artist.getName();
        for(Track track : artist.getTracks()) {
            TrackDTO trackDTO = new TrackDTO();
            trackDTO.setId(track.getId());
            trackDTO.setName(track.getName());
            trackDTO.setAlbumId(track.getAlbumId());
            trackDTO.setIssueDate(track.getIssueDate());
            trackDTO.setDuration(track.getDuration());
            trackDTO.setTrackMediaType(track.getTrackMediaType());
            tracks.add(trackDTO);
        }
    }
}
