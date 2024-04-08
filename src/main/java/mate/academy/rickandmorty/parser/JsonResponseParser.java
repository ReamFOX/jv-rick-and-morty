package mate.academy.rickandmorty.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterInfoDto;
import mate.academy.rickandmorty.dto.external.CharacterResponseDataDto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JsonResponseParser {
    private static final String RESULTS = "results";
    private static final String INFO = "info";
    private final ObjectMapper objectMapper;

    public CharacterInfoDto parseInfo(String responseBody) throws JsonProcessingException,
            JSONException {
        JSONObject info = new JSONObject(responseBody).getJSONObject(INFO);
        return objectMapper.readValue(info.toString(), CharacterInfoDto.class);
    }

    public List<CharacterResponseDataDto> parseResults(String responseBody)
            throws JSONException, JsonProcessingException {

        JSONArray results = new JSONObject(responseBody).getJSONArray(RESULTS);
        List<CharacterResponseDataDto> characters = new ArrayList<>();

        for (int i = 0; i < results.length(); i++) {
            JSONObject characterJson = results.getJSONObject(i);
            CharacterResponseDataDto character =
                    objectMapper.readValue(characterJson.toString(),
                            CharacterResponseDataDto.class);
            characters.add(character);
        }
        return characters;
    }
}
