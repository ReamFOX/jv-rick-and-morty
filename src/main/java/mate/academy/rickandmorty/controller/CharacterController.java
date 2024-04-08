package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Character browsing", description = "Endpoints for browsing characters")
@RequiredArgsConstructor
@RestController
@RequestMapping("/characters")
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping
    @Operation(summary = "Get all characters by name", description = "Get a list of all characters "
            + "whose name matches the parameter.")
    public List<CharacterDto> findAllByName(@RequestParam(name = "name") String param) {
        return characterService.findAllByName(param);
    }

    @GetMapping("/random")
    @Operation(summary = "Get a random character",
            description = "get a random character from among all of them")
    public CharacterDto getRandom() {
        return characterService.getRandom();
    }
}
