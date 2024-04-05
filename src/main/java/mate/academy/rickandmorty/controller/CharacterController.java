package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.service.character.CharacterService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Character browsing", description = "Endpoints for browsing characters")
@RequiredArgsConstructor
@RestController
@RequestMapping("/character")
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping
    @Operation(summary = "Get all characters", description = "Get a list of all characters")
    public List<CharacterDto> findAll(Pageable pageable) {
        return characterService.findAll(pageable);
    }

    @GetMapping("/random")
    @Operation(summary = "Get a random character",
            description = "get a random character from among all of them")
    public CharacterDto getRandom() {
        return characterService.getRandom();
    }
}
