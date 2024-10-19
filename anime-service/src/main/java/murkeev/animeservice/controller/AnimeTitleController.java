package murkeev.animeservice.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import murkeev.animeservice.dto.AnimeTitleDto;
import murkeev.animeservice.service.AnimeTitleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.Year;

@RestController
@RequestMapping("/api/v1/anime-titles")
@RequiredArgsConstructor
public class AnimeTitleController {
    private final AnimeTitleService animeTitleService;

    @GetMapping("/info-unsecured")
    public String infoUnsecured() {
        return "Info unsecured";
    }

    @GetMapping("/info-secured")
    public String infoSecured() {
        return "Info secured";
    }

    @GetMapping
    public Page<AnimeTitleDto> findAll(@RequestParam(value = "page_number") int pageNumber,
                                       @RequestParam(value = "page_size") int pageSize) {
        return animeTitleService.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/find-by-id/{id}")
    public AnimeTitleDto findById(@PathVariable("id") Long id) {
        return animeTitleService.findById(id);
    }

    @GetMapping("/find-by-release-date")
    public Page<AnimeTitleDto> findByReleaseDate(@RequestParam Year year,
                                                 @RequestParam(value = "page_number") int pageNumber,
                                                 @RequestParam(value = "page_size") int pageSize) {
        return animeTitleService.findByReleaseYear(year, PageRequest.of(pageNumber, pageSize));
    }


}
