package com.dt180g.project.abilities;

import com.dt180g.project.GameEngine;

/**
 * Base class for the abilities' hierarchy, declares common interface for all abilities.
 *
 * @author Samuel Thand
 */
public abstract class BaseAbility {
    private final int actionPointCost;
    private final int energyCost;

    /**
     * Base constructor, initializes members.
     *
     * @param actionPointCost The AP cost of this BaseAbility
     * @param energyCost The energy cost of this BaseAbility
     */
    protected BaseAbility(final int actionPointCost, final int energyCost) {
        this.actionPointCost = actionPointCost;
        this.energyCost = energyCost;
    }

    /**
     * Send command to the game engine to execute a character attack.
     * Uses the class AbilityInfo to pass needed information about the BaseAbility.
     *
     * @param abilityInfo Text information about the ability
     * @param targetAmount Amount of targets to attack
     * @param damageOrHealAmount Value of damage or heal
     * @param targetEnemies The ability will target enemies
     * @return The attack was successfully performed
     */
    protected final boolean performAbility(final String abilityInfo, final int targetAmount,
                                           final int damageOrHealAmount, final boolean targetEnemies) {

        String logMessage = String.format("%s (-%s AP, -%s Energy)", abilityInfo, actionPointCost, energyCost);

        AbilityInfo abilityToExecute = new AbilityInfo(logMessage,
                targetAmount, damageOrHealAmount, targetEnemies, isMagic(), isHeal());

        return GameEngine.INSTANCE.characterAttack(abilityToExecute);
    }

    /**
     * Get the AP cost of this BaseAbility.
     *
     * @return actionPointCost member
     */
    public final int getActionPointCost() {
        return actionPointCost;
    }

    /**
     * Get the energy cost of this BaseAbility.
     *
     * @return energyCost member
     */
    public final int getEnergyCost() {
        return energyCost;
    }

    /**
     * Check if this BaseAbility is magic.
     *
     * @return This ability is magic
     */
    public abstract boolean isMagic();

    /**
     * Check if this BaseAbility is a heal.
     *
     * @return This ability is a heal
     */
    public abstract boolean isHeal();

    /**
     * Get the amount of targets for this BaseAbility.
     *
     * @return Amount of targets for this BaseAbility
     */
    public abstract int getAmountOfTargets();

    /**
     * Used by BaseAbilities to call performAbility, with a base value
     * and amount of targets.
     *
     * @param baseValue Base value of the Ability
     * @param targetEnemies Target enemies
     * @return The attack was successfully performed
     */
    public abstract boolean execute(int baseValue, boolean targetEnemies);
}
