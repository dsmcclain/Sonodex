package dev.dylan.Sonodex.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private int duration;
    private MediaType mediaType;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "ARTIST_TRACKS",
        joinColumns = {
            @JoinColumn(name = "student_id", referencedColumnName = "id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "artist_id", referencedColumnName = "id")
        }
    )
    private Set<Artist> artists;

    public void addArtist(Artist artist) {
        this.artists.add(artist);
        artist.getTracks().add(this);
    }
}
