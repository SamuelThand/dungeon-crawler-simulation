package com.dt180g.project.characters;

import com.dt180g.project.abilities.BaseAbility;
import com.dt180g.project.support.Constants;
import com.dt180g.project.support.Randomizer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Base class for the character hierarchy, declares common interface for all characters.
 *
 * @author Samuel Thand
 */
public abstract class BaseCharacter implements Constants {
    private final CharacterStats characterStats;
    private final CharacterEquipment equipment;
    private List<BaseAbility> abilities;

    /**
     * Base constructor, initializes members.
     *
     * @param characterStats CharacterStats object with defined stats for this BaseCharacter
     */
    protected BaseCharacter(final CharacterStats characterStats) {
        this.characterStats = characterStats;
        this.equipment = new CharacterEquipment();
    }

    /**
     * Add abilities to this BaseCharacter.
     *
     * @param abilities List of BaseAbility objects
     */
    protected void addAbilities(final List<BaseAbility> abilities) {
        this.abilities = abilities;
    }

    /**
     * Get turn information for this BaseCharacter.
     *
     * @param characterType Character type to get turn information for
     * @return Formatted string of turn information
     */
    protected String getTurnInformation(final String characterType) {
        return String.format("%s | %s AP | %s HP | %s Energy", characterType,
                getActionPoints(), getHitPoints(), getEnergyLevel());
    }

    /**
     * Check if an ability can be afforded.
     *
     * @param costAP The AP cost of the ability
     * @param costEnergy the Energy cost of the Ability
     * @return The ability can be afforded
     */
    private boolean abilityIsAfforded(final int costAP, final int costEnergy) {
        return costAP <= characterStats.getCurrentActionPoints()
                && costEnergy <= characterStats.getCurrentEnergyLevel();
    }

    /**
     * Determine the base value for the ability, depending on its type.
     *
     * @param ability BaseAbility to get the base value for
     * @return The base value for the ability
     */
    private int determineAbilityBaseValue(final BaseAbility ability) {
        int attackRate = getCharacterStats().getAttackRate();

        int abilityBaseValue;
        if (ability.isMagic()) {
            if (ability.isHeal()) {
                abilityBaseValue = attackRate + characterStats.getHealingPower();
            } else {
                abilityBaseValue = attackRate + characterStats.getMagicPower();
            }
        } else {
            abilityBaseValue = attackRate + characterStats.getPhysicalPower()
                    + getEquipment().getTotalWeaponDamage();
        }

        return abilityBaseValue;
    }

    /**
     * Execute actions and register resources cost for these actions.
     * Iterates over a queue of BaseAbilities, executes it if possible
     * and registers the AP and Energy cost.
     *
     * @param targetEnemies Target enemies
     */
    protected void executeActions(final boolean targetEnemies) {
        Deque<BaseAbility> queuedAbilities = determineActions();

        for (BaseAbility ability : queuedAbilities) {
            int costAP = ability.getActionPointCost();
            int costEnergy = ability.getEnergyCost();

            if (abilityIsAfforded(costAP, costEnergy)) {
                int abilityBaseValue = determineAbilityBaseValue(ability);

                // Tries to execute current ability and stores result.
                boolean abilityWasExecuted = ability.execute(abilityBaseValue, targetEnemies);

                if (!abilityWasExecuted) {
                    // Failed execution means no target was available, stops execution of queued abilities.
                    break;
                } else {
                    // Execution successful, AP and energy cost is registered.
                    CharacterStats stats = getCharacterStats();
                    stats.adjustActionPoints(-costAP);
                    stats.adjustEnergyLevel(-costEnergy);
                }
            }
        }
    }

    /**
     * Select a random index in a list.
     *
     * @param list to index into
     * @return random index from the list
     */
    private int getRandomIndex(final List<?> list) {
        int randomIndex = 0;
        if (list.size() != 0) {
            randomIndex = Randomizer.INSTANCE.getRandomValue(list.size() - 1);
        }

        return randomIndex;
    }

    /**
     * Determine what actions to execute during a turn.
     *
     * @return Queue of abilities
     */
    private Deque<BaseAbility> determineActions() {
        List<BaseAbility> abilitiesFromMember = getAbilities();
        ArrayDeque<BaseAbility> randomAbilities = new ArrayDeque<>();

        for (int i = 0; i < ACTIONS_PER_TURN; i++) {
            int randomindex = getRandomIndex(abilitiesFromMember);
            BaseAbility randomAbility = abilitiesFromMember.get(randomindex);
            randomAbilities.push(randomAbility);
        }

        return randomAbilities;
    }

    /**
     * Register incoming damage on this BaseCharacter.
     *
     * @param baseDamage Incoming base damage
     * @param isMagicDamage Incoming damage is magic
     * @return Two item list with 0: mitigated damage and 1: actual damage
     */
    public List<Integer> registerDamage(final int baseDamage, final boolean isMagicDamage) {
        CharacterStats stats = getCharacterStats();

        int mitigatedDamage;
        if (isMagicDamage) {
            mitigatedDamage = stats.getDefenceRate();
        } else {
            mitigatedDamage = stats.getDefenceRate() + getEquipment().getTotalArmorProtection();
        }

        // Mitigate damage down to 0, but not lower
        int actualDamage = Math.max(0, baseDamage - mitigatedDamage);

        stats.adjustHitPoints(-actualDamage);

        return new ArrayList<>(Arrays.asList(mitigatedDamage, actualDamage));
    }

    /**
     * Register incoming healing on this BaseCharacter.
     *
     * @param incomingHeal Value of incoming heal
     * @return Current HP after the heal
     */
    public int registerHealing(final int incomingHeal) {
        CharacterStats stats = getCharacterStats();

        int HPAmountUntilMax = stats.getTotalHitPoints() - stats.getCurrentHitPoints();
        int healValue = Math.min(HPAmountUntilMax, incomingHeal);

        stats.adjustHitPoints(healValue);

        return stats.getCurrentHitPoints();
    }

    /**
     * Replenish some AP and Energy after a round.
     */
    public void roundReset() {
        CharacterStats stats = getCharacterStats();

        int APAmountUntilMax = stats.getTotalActionPoints() - stats.getCurrentActionPoints();
        int energyAmountUntilMax = stats.getTotalEnergyLevel() - stats.getCurrentEnergyLevel();

        int APregen = Math.min(APAmountUntilMax, ROUND_RESET_AP);
        int Energyregen = Math.min(energyAmountUntilMax, ROUND_RESET_ENERGY);

        stats.adjustActionPoints(APregen);
        stats.adjustEnergyLevel(Energyregen);
    }

    /**
     * Log turn information and call executeActions.
     */
    public abstract void doTurn();

    /**
     * Get the name of this BaseCharacter.
     *
     * @return The name of this BaseCharacter
     */
    public abstract String getCharacterName();

    /**
     * Get the stats of this BaseCharacter.
     *
     * @return The stats of this BaseCharacter
     */
    public CharacterStats getCharacterStats() {
        return characterStats;
    }

    /**
     * Get the equipment of this BaseCharacter.
     *
     * @return The equipment of this BaseCharacter
     */
    public CharacterEquipment getEquipment() {
        return equipment;
    }

    /**
     * Get the AP of this BaseCharacter.
     *
     * @return The AP of this BaseCharacter
     */
    public int getActionPoints() {
        return getCharacterStats().getCurrentActionPoints();
    }

    /**
     * Get the current HP of this BaseCharacter.
     *
     * @return The current HP of this BaseCharacter
     */
    public int getHitPoints() {
        return getCharacterStats().getCurrentHitPoints();
    }

    /**
     * Get the current Energy of this BaseCharacter.
     *
     * @return The current Energy of this BaseCharacter
     */
    public int getEnergyLevel() {
        return getCharacterStats().getCurrentEnergyLevel();
    }

    /**
     * Get the abilities of this BaseCharacter.
     *
     * @return the abilities of this BaseCharacter
     */
    public List<BaseAbility> getAbilities() {
        return abilities;
    }

    /**
     * Check if this BaseCharacter is dead.
     *
     * @return This BaseCharacter is dead
     */
    public boolean isDead() {
        return getCharacterStats().getCurrentHitPoints() <= 0;
    }

    /**
     * Get the full information about this BaseCharacter.
     *
     * @return Full information about this BaseCharacter as a formatted string
     */
    @Override
    public String toString() {
        final int HEADER_WIDTH = 42;

        String headerBorder = "*".repeat(HEADER_WIDTH);
        String padding = " ".repeat(((HEADER_WIDTH - getCharacterName().length()) / 2));

        String characterHeader = ANSI_WHITE + String.format("%s\n%s%s%s\n%s", headerBorder,
        padding, getCharacterName().toUpperCase(), padding, headerBorder);

        return characterHeader + "\n" + getCharacterStats().toString() + "\n" + getEquipment().toString() + ANSI_RESET;
    }
}
