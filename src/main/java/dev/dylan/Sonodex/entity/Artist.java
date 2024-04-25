package dev.dylan.Sonodex.entity;

import com.fasterxml.jackson.annotation.JsonView;
import dev.dylan.Sonodex.view.JsonViewProfiles;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Artist {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Valid
    @JsonView({JsonViewProfiles.Artist.class}) // only render tracks when rendering the Artist view
    @ManyToMany(mappedBy = "artists", cascade = CascadeType.PERSIST)
    @Builder.Default
    private Set<Track> tracks = new HashSet<>();

    public void addTrack(Track track) {
        tracks.add(track);
        track.getArtists().add(this);
    }


}
