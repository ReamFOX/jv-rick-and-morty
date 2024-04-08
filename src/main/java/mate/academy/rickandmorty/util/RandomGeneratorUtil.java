package mate.academy.rickandmorty.util;

import java.util.Random;

public class RandomGeneratorUtil {
    private static final int CORRECTION = 1;
    private static final Random random = new Random();

    private RandomGeneratorUtil() {
    }

    public static Long generateRandomId(Long quantity) {
        return random.nextLong(1, quantity) + CORRECTION;
    }
}
