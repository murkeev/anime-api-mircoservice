package murkeev.animeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import murkeev.animeservice.enums.Genre;

import java.time.Year;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AnimeTitleDto {
    private String titleEng;

    private String originalTitle;
    private Set<Genre> genres;
    private Year releaseYear;
    private String description;
    private int seasonCount;
    private int episodeCount;
}
