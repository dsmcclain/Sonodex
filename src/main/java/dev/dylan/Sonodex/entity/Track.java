package dev.dylan.Sonodex.entity;

import com.fasterxml.jackson.annotation.JsonView;
import dev.dylan.Sonodex.view.JsonViewProfiles;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Track {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long albumId;
    private LocalDate issueDate;
    private String duration;
    private TrackMediaType trackMediaType;

    @Valid
    @JsonView(JsonViewProfiles.Track.class)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "ARTIST_TRACKS",
        joinColumns = {
            @JoinColumn(name = "track_id", referencedColumnName = "id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "artist_id", referencedColumnName = "id")
        }
    )
    @Builder.Default
    private Set<Artist> artists = new HashSet<>();

    public void addArtist(Artist artist) {
        this.artists.add(artist);
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", albumId=" + albumId +
                ", issueDate=" + issueDate +
                ", duration=" + duration +
                ", trackMediaType=" + trackMediaType +
                ", artists=" + artists +
                '}';
    }
}
