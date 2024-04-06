package mate.academy.rickandmorty.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterInfoDto;
import mate.academy.rickandmorty.dto.external.CharacterResponseDataDto;
import mate.academy.rickandmorty.exception.FetchingDataException;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.parser.JsonInfoParser;
import mate.academy.rickandmorty.parser.JsonResultsParser;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.util.RandomIdGenerator;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
public class ApiCharacterController {
    private static final String BASE_URL = "https://rickandmortyapi.com/api/character";
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;
    private final JsonResultsParser jsonResultsParser;
    private final JsonInfoParser jsonInfoParser;

    @PostConstruct
    public void fetchAndStoreCharacters() {
        RestTemplate restTemplate = new RestTemplate();
        List<Character> allCharacters = new ArrayList<>();
        String url = BASE_URL;
        while (url != null) {
            String responseBody = getResponseBody(url, restTemplate);
            try {
                CharacterInfoDto characterInfoDto = jsonInfoParser.parse(responseBody);
                List<CharacterResponseDataDto> charactersDto =
                        jsonResultsParser.parse(responseBody);
                List<Character> characters = charactersDto.stream()
                        .map(characterMapper::toModel)
                        .toList();
                allCharacters.addAll(characters);
                url = characterInfoDto.getNext();
            } catch (JSONException | JsonProcessingException e) {
                throw new FetchingDataException("Can`t parse JSON response", e);
            }
        }
        RandomIdGenerator.setQuantity(allCharacters.size());
        characterRepository.saveAll(allCharacters);
    }

    private String getResponseBody(String url, RestTemplate restTemplate) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("HTTP status is not ok");
        }
        return response.getBody();
    }
}

