package dev.dylan.Sonodex.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artist {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "artists", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    //todo: improve this by creating a JSONView https://stackoverflow.com/questions/67886252/spring-boot-jpa-infinite-loop-many-to-many
    @JsonIgnore
    private Set<Track> tracks;
}
