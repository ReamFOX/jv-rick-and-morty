package mate.academy.rickandmorty.dto.external;

import lombok.Data;

@Data
public class CharacterInfoDto {
    private int count;
    private int pages;
    private String next;
}
