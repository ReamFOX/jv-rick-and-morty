package mate.academy.rickandmorty.service;

import java.util.List;
import mate.academy.rickandmorty.dto.external.CharacterResponseDataDto;
import mate.academy.rickandmorty.dto.internal.CharacterDto;

public interface CharacterService {
    List<CharacterDto> saveAll(List<CharacterResponseDataDto> responseDataDto);

    List<CharacterDto> findAllByName(String param);

    CharacterDto getRandom();
}
