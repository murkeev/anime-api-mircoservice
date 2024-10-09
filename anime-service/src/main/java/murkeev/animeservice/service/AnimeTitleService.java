package murkeev.animeservice.service;

import lombok.RequiredArgsConstructor;
import murkeev.animeservice.dto.AnimeTitleDto;
import murkeev.animeservice.model.AnimeTitle;
import murkeev.animeservice.repository.AnimeTitleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;

@Service
@RequiredArgsConstructor
public class AnimeTitleService {
    private final AnimeTitleRepository animeTitleRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Page<AnimeTitleDto> findAll(Pageable pageable) {
        Page<AnimeTitle> animeTitles = animeTitleRepository.findAllTitles(pageable);
        if (animeTitles.isEmpty()) {
            throw new RuntimeException("No anime titles found");
        }
        return animeTitles.map(animeTitle -> modelMapper.map(animeTitle, AnimeTitleDto.class));
    }

    @Transactional(readOnly = true)
    public AnimeTitleDto findById(Long id) {
        AnimeTitle animeTitle = animeTitleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anime title not found"));
        return modelMapper.map(animeTitle, AnimeTitleDto.class);
    }

    @Transactional(readOnly = true)
    public Page<AnimeTitleDto> findByReleaseYear(Year releaseYear, Pageable pageable) {
        Page<AnimeTitle> animeTitles = animeTitleRepository.findByReleaseYear(releaseYear, pageable);
        if (animeTitles.isEmpty()) {
            throw new RuntimeException("No anime titles found");
        }
        return animeTitles.map(animeTitle -> modelMapper.map(animeTitle, AnimeTitleDto.class));
    }

    @Transactional
    public void create(AnimeTitleDto animeTitleDto) {
        AnimeTitle animeTitle = modelMapper.map(animeTitleDto, AnimeTitle.class);
        try {
            animeTitleRepository.save(animeTitle);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating anime title");
        }
    }

    @Transactional
    public void removeById(Long id) {
        AnimeTitle animeTitle = animeTitleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anime title not found"));
        animeTitleRepository.delete(animeTitle);
    }

    @Transactional
    public void removeByTitleEng(String titleEng) {
        AnimeTitle animeTitle = animeTitleRepository.findByTitleEng(titleEng)
                .orElseThrow(() -> new RuntimeException("Anime title not found"));
        animeTitleRepository.delete(animeTitle);
    }

}
