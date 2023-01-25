package com.dt180g.project.stats;

/**
 * Concrete class for the trait stats.
 *
 * @author Samuel Thand
 */
public class Trait extends BaseStat {

    /**
     * Constructor, uses superclass constructor to initialize members.
     *
     * @param statName The name of this Trait
     * @param baseValue The base value of this Trait
     */
    public Trait(final String statName, final int baseValue) {
        super(statName, baseValue);
    }
}
