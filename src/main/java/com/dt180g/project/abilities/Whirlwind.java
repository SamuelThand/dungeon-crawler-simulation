package com.dt180g.project.abilities;

import com.dt180g.project.support.Constants;

/**
 * Concrete class for the ability Whirlwind.
 *
 * @author Samuel Thand
 */
public class Whirlwind extends BaseAbility implements Constants {

    /**
     * Constructor, initializes members.
     */
    public Whirlwind() {
        super(HIGHEST_AP_COST, HIGH_ENERGY_COST);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isMagic() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isHeal() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getAmountOfTargets() {
        return ABILITY_GROUP_TARGET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean execute(final int baseValue, final boolean targetEnemies) {
        return super.performAbility(toString(), getAmountOfTargets(), baseValue, targetEnemies);
    }

    /**
     * Get information about the BaseAbility.
     *
     * @return ability name
     */
    @Override
    public final String toString() {
        return ABILITY_WHIRLWIND;
    }
}
