package mate.academy.rickandmorty.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Component;

@Component
public class RickAndMortyClient {
    private static final String BASE_URL = "https://rickandmortyapi.com/api/%s";


    public Object getData(String type) {
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL.formatted(type);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
