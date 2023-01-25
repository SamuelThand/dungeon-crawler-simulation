package com.dt180g.project.stats;

import com.dt180g.project.support.Constants;

/**
 * Concrete class for the combat stats, based on attributes and traits.
 *
 * @author Samuel Thand
 */
public class CombatStat extends BaseStat implements Constants {
    private final BaseStat attributeReliance;
    private final BaseStat traitReliance;


    /**
     * Constructor, initialize members with baseValue of 0 since calculation of the base value
     * is done in the overridden getBaseValue() method.
     *
     * @param statName The name of this CombatStat
     * @param attributeReliance The attribute this CombatStat relies on
     * @param traitReliance The trait this CombatStat relies on
     */
    public CombatStat(final String statName, final BaseStat attributeReliance, final BaseStat traitReliance) {
        super(statName, 0);
        this.attributeReliance = attributeReliance;
        this.traitReliance = traitReliance;
    }

    /**
     * Calculate the base value of this CombatStat
     * based on the value of the attribute and trait reliance.
     *
     * @return The calculated base value
     */
    @Override
    public final int getBaseValue() {
        double calculatedAttributeValue = attributeReliance.getModifiedValue() * Constants.COMBAT_STAT_MULTIPLIER;
        double calculatedTraitValue = traitReliance.getModifiedValue() * Constants.COMBAT_STAT_MULTIPLIER;

        return (int) Math.round(calculatedAttributeValue + calculatedTraitValue);
    }
}
