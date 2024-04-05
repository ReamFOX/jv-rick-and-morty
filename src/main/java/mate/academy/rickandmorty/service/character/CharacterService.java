package mate.academy.rickandmorty.service.character;

import java.util.List;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.model.Character;
import org.springframework.data.domain.Pageable;

public interface CharacterService {
    void saveAll(List<Character> characterList);

    List<CharacterDto> findAll(Pageable pageable);

    CharacterDto getRandom();
}