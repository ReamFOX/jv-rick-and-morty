package mate.academy.rickandmorty.controller;

import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.service.RickAndMortyClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/character")
public class CharacterController {
    private final RickAndMortyClient client;

    @GetMapping
    public void test() {
        client.getAllCharacters();
    }
}
