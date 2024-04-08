package mate.academy.rickandmorty.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterInfoDto;
import mate.academy.rickandmorty.dto.external.CharacterResponseDataDto;
import mate.academy.rickandmorty.exception.FetchingDataException;
import mate.academy.rickandmorty.exception.HttpStatusCodeNotOkException;
import mate.academy.rickandmorty.parser.JsonResponseParser;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ApiClient {
    private static final String BASE_URL = "https://rickandmortyapi.com/api/character";
    private final RestTemplate restTemplate;
    private final CharacterService characterService;
    private final JsonResponseParser jsonResponseParser;

    public void fetchAndStoreCharacters() {
        List<CharacterResponseDataDto> allCharacters = new ArrayList<>();
        String url = BASE_URL;
        while (url != null) {
            String responseBody = getResponseBody(url, restTemplate);
            try {
                CharacterInfoDto characterInfoDto =
                        jsonResponseParser.parseToCharacterInfo(responseBody);
                List<CharacterResponseDataDto> charactersDto =
                        jsonResponseParser.parseToCharacterData(responseBody);
                allCharacters.addAll(charactersDto);
                url = characterInfoDto.getNext();
            } catch (JSONException | JsonProcessingException e) {
                throw new FetchingDataException("Can`t parse JSON response", e);
            }
        }
        characterService.saveAll(allCharacters);
    }

    private String getResponseBody(String url, RestTemplate restTemplate) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new HttpStatusCodeNotOkException(response.getStatusCode(), response.getBody());
        }
        return response.getBody();
    }
}
