package mate.academy.rickandmorty.config;

import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.service.ApiClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CharacterFetcher implements CommandLineRunner {
    private final ApiClient apiClient;

    @Override
    public void run(String... args) throws Exception {
        apiClient.fetchAndStoreCharacters();
    }
}
