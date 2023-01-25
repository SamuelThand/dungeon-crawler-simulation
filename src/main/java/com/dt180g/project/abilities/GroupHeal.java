package com.dt180g.project.abilities;

import com.dt180g.project.support.Constants;

/**
 * Concrete class for the ability GroupHeal.
 *
 * @author Samuel Thand
 */
public class GroupHeal extends BaseAbility implements Constants {
    private final String magicalPhrase;

    /**
     * Constructor, initializes members.
     */
    public GroupHeal() {
        super(HIGHEST_AP_COST, HIGH_ENERGY_COST);
        magicalPhrase = MAGICAL_PHRASE_3;
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
        return true;
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
     *
     * Negates the values of targetEnemies and baseValue in order
     * to target the own team and do negative damage(heal).
     */
    @Override
    public final boolean execute(int baseValue, boolean targetEnemies) {
        targetEnemies = !targetEnemies;
        baseValue = -(baseValue);
        return super.performAbility(toString(), getAmountOfTargets(), baseValue, targetEnemies);
    }

    /**
     * Get information about the BaseAbility.
     *
     * @return Magical phrase, ability name in a formatted string
     */
    @Override
    public final String toString() {
        return String.format("%s: %s", magicalPhrase, ABILITY_GROUP_HEAL);
    }
}
