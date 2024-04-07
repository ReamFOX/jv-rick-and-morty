package mate.academy.rickandmorty.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.util.RandomIdGenerator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository repository;
    private final CharacterMapper characterMapper;

    @Override
    public List<CharacterDto> findAllByName(String param) {
        return repository.findByNameLikeIgnoreCase(param)
                .stream()
                .map(characterMapper::toDto)
                .toList();
    }

    @Override
    public CharacterDto getRandom() {
        Long randomId = RandomIdGenerator.generateRandomId();
        Character character = repository.findById(randomId).orElseThrow(
                () -> new EntityNotFoundException("Character with id " + randomId
                        + " doesn't exist"));
        return characterMapper.toDto(character);
    }
}
