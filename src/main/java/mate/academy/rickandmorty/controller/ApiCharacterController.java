package mate.academy.rickandmorty.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.internal.CharacterIndex;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterInfoDto;
import mate.academy.rickandmorty.dto.external.CharacterResponseDataDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.parser.JsonDataParser;
import mate.academy.rickandmorty.parser.JsonInfoParser;
import mate.academy.rickandmorty.repository.CharacterRepository;
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
    private final JsonDataParser jsonDataParser;
    private final JsonInfoParser jsonInfoParser;

    @PostConstruct
    public void fetchAndStoreCharacters() {
        String url = BASE_URL;
        List<Character> allCharacters = new ArrayList<>();
        do {
            String responseBody = getResponseBody(url);
            try {
                CharacterInfoDto characterInfoDto = jsonInfoParser.parse(responseBody);
                List<CharacterResponseDataDto> charactersDto = jsonDataParser.parse(responseBody);
                List<Character> characters = charactersDto.stream()
                        .map(characterMapper::toModel)
                        .toList();
                allCharacters.addAll(characters);
                url = characterInfoDto.getNext();
            } catch (JSONException | JsonProcessingException e) {
                throw new RuntimeException("Can`t parse JSON data response", e);
            }
        } while (url != null);
        characterRepository.saveAll(allCharacters);
    }

    private String getResponseBody(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("HTTP status is not ok");
        }
        return response.getBody();
    }
}
