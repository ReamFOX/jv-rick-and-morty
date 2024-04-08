package mate.academy.rickandmorty.service;

import lombok.RequiredArgsConstructor;
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
