package com.dt180g.project.abilities;

import com.dt180g.project.support.Constants;

/**
 * Concrete class for the ability ElementalBolt.
 *
 * @author Samuel Thand
 */
public class ElementalBolt extends BaseAbility implements Constants {
    private final String magicalPhrase;
    private final String element;

    /**
     * Constructor, initializes members.
     *
     * @param element The element of the ability
     */
    public ElementalBolt(final String element) {
        super(MEDIUM_AP_COST, LOW_ENERGY_COST);
        this.element = element;
        this.magicalPhrase = MAGICAL_PHRASE_2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isMagic() {
        return true;
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
        return ABILITY_SINGLE_TARGET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean execute(final int baseValue, final boolean targetEnemies) {
        return super.performAbility(toString(), getAmountOfTargets(),
                baseValue * SINGLE_TARGET_ABILITY_MULTIPLIER, targetEnemies);
    }

    /**
     * Get information about the BaseAbility.
     *
     * @return Magical phrase, element, ability name in a formatted string
     */
    @Override
    public final String toString() {
        return String.format("%s: %s %s", magicalPhrase, element, ABILITY_ELEMENTAL_BOLT);
    }
}

