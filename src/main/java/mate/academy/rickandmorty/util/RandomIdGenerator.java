package mate.academy.rickandmorty.util;

import java.util.Random;
import lombok.Setter;

public class RandomIdGenerator {
    private static final int CORRECTION = 1;
    private static final Random random = new Random();
    @Setter
    private static int quantity;

    public static Long generateRandomId() {
        return random.nextLong(quantity) + CORRECTION;
    }
}
