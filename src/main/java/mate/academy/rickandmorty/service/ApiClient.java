package mate.academy.rickandmorty.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterInfoDto;
import mate.academy.rickandmorty.dto.external.CharacterResponseDataDto;
import mate.academy.rickandmorty.exception.FetchingDataException;
import mate.academy.rickandmorty.parser.JsonResponseParser;
import mate.academy.rickandmorty.util.RandomIdGenerator;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ApiClient {
    private static final String BASE_URL = "https://rickandmortyapi.com/api/character";
    private final CharacterService characterService;
    private final JsonResponseParser jsonResponseParser;

    @PostConstruct
    public void fetchAndStoreCharacters() {
        RestTemplate restTemplate = new RestTemplate();
        List<CharacterResponseDataDto> allCharacters = new ArrayList<>();
        String url = BASE_URL;
        while (url != null) {
            String responseBody = getResponseBody(url, restTemplate);
            try {
                CharacterInfoDto characterInfoDto = jsonResponseParser.parseInfo(responseBody);
                List<CharacterResponseDataDto> charactersDto =
                        jsonResponseParser.parseResults(responseBody);
                allCharacters.addAll(charactersDto);
                url = characterInfoDto.getNext();
            } catch (JSONException | JsonProcessingException e) {
                throw new FetchingDataException("Can`t parse JSON response", e);
            }
        }
        RandomIdGenerator.setQuantity(allCharacters.size());
        characterService.saveAll(allCharacters);
    }

    private String getResponseBody(String url, RestTemplate restTemplate) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("HTTP status is not ok");
        }
        return response.getBody();
    }
}

