package dev.dylan.Sonodex.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Track {
    @Id
    @GeneratedValue
    private Long id;
    private Long albumId;
    private LocalDate issueDate;
    private int duration;
    private MediaType mediaType;

    @ManyToMany
    private Set<Artist> artists;

}
