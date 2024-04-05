package mate.academy.rickandmorty.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterInfoDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JsonInfoParser {
    private final ObjectMapper objectMapper;

    public CharacterInfoDto parse(String responseBody) throws JsonProcessingException,
            JSONException {
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONObject info = jsonObject.getJSONObject("info");
        return objectMapper.readValue(info.toString(), CharacterInfoDto.class);
    }
}
