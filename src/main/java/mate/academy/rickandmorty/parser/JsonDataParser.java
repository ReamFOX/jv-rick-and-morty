package mate.academy.rickandmorty.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterResponseDataDto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JsonDataParser {
    private final ObjectMapper objectMapper;

    public List<CharacterResponseDataDto> parse(String responseBody)
            throws JSONException, JsonProcessingException {
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray results = jsonObject.getJSONArray("results");
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
