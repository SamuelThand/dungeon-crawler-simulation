package com.dt180g.project.support;

import java.util.Random;

/**
 * Support class used for randomising numeric values.
 */
public final class Randomizer {
    public static final Randomizer INSTANCE = new Randomizer();
    private final Random generator = new Random();

    /**
     * Private construction since the class is a singleton.
     */
    private Randomizer() { }

    /**
     * Used internally by Randomizer to produce random number within stated bounds.
     * @param min lower bound for randomisation.
     * @param max upper bound for randomisation.
     * @return randomised numeric value.
     */
    private int produceRandomValue(final int min, final int max) {
        int bounds = (max - min) + 1;  // the bounds needs to be within interval min - max
        return generator.nextInt(bounds) + min;
    }

    /**
     * Used by clients to retrieve random numeric value, stating upper bound.
     * @param max upper bound for randomisation.
     * @return randomised numeric value.
     */
    public int getRandomValue(final int max) {
        return produceRandomValue(0, max);
    }

    /**
     * Used by clients to retrieve random numeric value, stating both lower and upper bound.
     * @param min lower bound for randomisation.
     * @param max upper bound for randomisation.
     * @return randomised numeric value.
     */
    public int getRandomValue(final int min, final int max) {
        return produceRandomValue(min, max);
    }


}
