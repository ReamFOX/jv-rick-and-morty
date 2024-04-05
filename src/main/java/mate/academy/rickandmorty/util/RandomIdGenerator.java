package mate.academy.rickandmorty.util;

import java.util.Random;

public class RandomIdGenerator {
    private static final int CORRECTION = 1;
    private static final Random random = new Random();

    public static Long generateId(int numOfEntities) {
        return random.nextLong(numOfEntities) + CORRECTION;
    }
}
