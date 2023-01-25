package com.dt180g.project.stats;

/**
 * Concrete class for the attribute stats.
 *
 * @author Samuel Thand
 */
public class Attribute extends BaseStat {

    /**
     * Constructor, uses superclass constructor to initialize members.
     *
     * @param statName The name of this Attribute
     * @param baseValue The base value of this Attribute
     */
    public Attribute(final String statName, final int baseValue) {
        super(statName, baseValue);
    }
}
