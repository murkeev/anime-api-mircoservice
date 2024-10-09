package murkeev.animeservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import murkeev.animeservice.enums.Genre;

import java.time.Year;
import java.util.Set;

@Entity
@Table(name = "anime_title")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnimeTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title_eng", unique = true, nullable = false)
    private String titleEng;

    @Column(name = "original_title")
    private String originalTitle;

    @ElementCollection(targetClass = Genre.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "anime_genres", joinColumns = @JoinColumn(name = "anime_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Set<Genre> genres;

    @Column(name = "released_at")
    private Year releaseYear;

    private String description;

    @Column(name = "count_of_seasons")
    private int seasonCount;

    @Column(name = "count_of_episodes")
    private int episodeCount;

}
