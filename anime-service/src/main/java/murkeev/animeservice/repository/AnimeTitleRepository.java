package murkeev.animeservice.repository;

import murkeev.animeservice.model.AnimeTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.Optional;

@Repository
public interface AnimeTitleRepository extends JpaRepository<AnimeTitle, Long> {

    @Query("SELECT at FROM AnimeTitle at")
    Page<AnimeTitle> findAllTitles(Pageable pageable);

    @Query("SELECT at FROM AnimeTitle at where at.titleEng = :titleEng")
    Optional<AnimeTitle> findByTitleEng(String titleEng);

//    @Query("SELECT at FROM AnimeTitle at where at.originalTitle = :originalTitle")
//    Optional<AnimeTitle> findByOriginalTitle(String originalTitle);

    @Query("SELECT at FROM AnimeTitle at where at.releaseYear = :releaseYear")
    Page<AnimeTitle> findByReleaseYear(Year releaseYear, Pageable pageable);


}
