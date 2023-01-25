package com.dt180g.project.characters;

import com.dt180g.project.stats.Attribute;
import com.dt180g.project.stats.BaseStat;
import com.dt180g.project.stats.CombatStat;
import com.dt180g.project.stats.StatsManager;
import com.dt180g.project.stats.Trait;
import com.dt180g.project.support.Constants;
import com.dt180g.project.support.IOHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for managing a characters stats by storing and
 * providing a public interface for accessing these.
 *
 * @author Samuel Thand
 */
public class CharacterStats implements Constants {

    private final Map<String, BaseStat> stats = new HashMap<>();

    /**
     * Constructor, initializes member stats.
     *
     * @param attributeBaseValues Base values for the attributes
     */
    public CharacterStats(final List<Integer> attributeBaseValues) {
        initializeAttributes(attributeBaseValues);
        initializeTraits();
        initializeCombatStats();
    }

    /**
     * Initialize Attributes.
     * Uses the stat name lists in StatsManager to initialize the stats in the
     * same order. Uses parameter for determining the attribute base values.
     *
     * @param attributeBaseValues Base values for the attributes
     */
    private void initializeAttributes(final List<Integer> attributeBaseValues) {
        int attributeIndex = 0;
        for (String attributeName : StatsManager.INSTANCE.getAttributeNames()) {
            stats.put(attributeName, new Attribute(attributeName,
                    attributeBaseValues.get(attributeIndex) * ATTRIBUTE_BASE_VALUE));
            attributeIndex += 1;
        }
    }

    /**
     * Initialize Traits.
     * Uses the stat name lists in StatsManager to initialize the stats in the
     * same order. Uses the correct constant from Constants to determine trait base values.
     */
    private void initializeTraits() {
        for (String traitName : StatsManager.INSTANCE.getTraitNames()) {
            int belongingBaseValue = switch (traitName) {
                case TRAIT_VITALITY -> TRAIT_VITALITY_BASE_VALUE;
                case TRAIT_ENERGY -> TRAIT_ENERGY_BASE_VALUE;
                case TRAIT_ATTACK_RATE -> TRAIT_ATTACK_RATE_BASE_VALUE;
                case TRAIT_DEFENSE_RATE -> TRAIT_DEFENCE_RATE_BASE_VALUE;
                default -> throw new IllegalStateException("Unexpected trait name");
            };

            stats.put(traitName, new Trait(traitName, belongingBaseValue));
        }
    }

    /**
     * Initialize CombatStats.
     * Uses the stat name lists in StatsManager to initialize the stats in the
     * same order. Uses the correct constant from Constants to determine combat stat base values.
     */
    private void initializeCombatStats() {
        for (String combatStatName : StatsManager.INSTANCE.getCombatStatNames()) {
            BaseStat attributeReliance = switch (combatStatName) {
                case COMBAT_STAT_ACTION_POINTS -> getStat(ATTRIBUTE_DEXTERITY);
                case COMBAT_STAT_PHYSICAL_POWER -> getStat(ATTRIBUTE_STRENGTH);
                case COMBAT_STAT_MAGIC_POWER -> getStat(ATTRIBUTE_INTELLIGENCE);
                case COMBAT_STAT_HEALING_POWER -> getStat(ATTRIBUTE_WILLPOWER);
                default -> throw new IllegalStateException("Unexpected combat stat name");
            };

            stats.put(combatStatName, new CombatStat(combatStatName, attributeReliance, getStat(TRAIT_ATTACK_RATE)));
        }
    }

    /**
     * Adjust the current AP.
     *
     * @param value Positive or negative value to adjust AP with
     */
    public final void adjustActionPoints(final int value) {
        adjustStatDynamicModifier(COMBAT_STAT_ACTION_POINTS, value);
    }

    /**
     * Adjust the current HP.
     *
     * @param value Positive or negative value to adjust HP with
     */
    public final void adjustHitPoints(final int value) {
        adjustStatDynamicModifier(TRAIT_VITALITY, value);
    }

    /**
     * Adjust the current Energy.
     *
     * @param value Positive or negative value to adjust Energy with
     */
    public final void adjustEnergyLevel(final int value) {
        getStat(TRAIT_ENERGY).adjustDynamicModifier(value);
    }

    /**
     * Adjust the static modifier of a stat.
     *
     * @param statName Name of the stat to modify static modifier on
     * @param value Positive or negative value to adjust the static modifier with
     */
    public final void adjustStatStaticModifier(final String statName, final int value) {
        getStat(statName).adjustStaticModifier(value);
    }

    /**
     * Adjust the dynamic modifier of a stat.
     *
     * @param statName Name of the stat to modify dynamic modifier on
     * @param value Positive or negative value to adjust the dynamic modifier with
     */
    public final void adjustStatDynamicModifier(final String statName, final int value) {
        getStat(statName).adjustDynamicModifier(value);
    }

    /**
     * Reset the current AP to the base value, gear bonuses included.
     */
    public final void resetActionPoints() {
        getStat(COMBAT_STAT_ACTION_POINTS).resetDynamicModifier();
    }

    /**
     * Reset the current HP to the base value, gear bonuses included.
     */
    public final void resetHitPoints() {
        getStat(TRAIT_VITALITY).resetDynamicModifier();
    }

    /**
     * Reset the current Energy to the base value, gear bonuses included.
     */
    public final void resetEnergyLevel() {
        getStat(TRAIT_ENERGY).resetDynamicModifier();
    }

    /**
     * Get the stat with the provided name.
     *
     * @param statName Name of the stat
     * @return The stat
     */
    public final BaseStat getStat(final String statName) {
        return stats.get(statName);
    }

    /**
     * Get the modified value of the stat with the provided name.
     *
     * @param statName Name of the stat
     * @return The modified value of the stat
     */
    public final int getStatValue(final String statName) {
        return getStat(statName).getModifiedValue();
    }

    /**
     * Get the base value of AP.
     *
     * @return The base value of AP
     */
    public final int getTotalActionPoints() {
        BaseStat statAP = getStat(COMBAT_STAT_ACTION_POINTS);
        return statAP.getBaseValue() + statAP.getStaticModifier();
    }

    /**
     * Get the current value of AP.
     *
     * @return The current value of AP
     */
    public final int getCurrentActionPoints() {
        return getStatValue(COMBAT_STAT_ACTION_POINTS);
    }

    /**
     * Get the base value of HP.
     *
     * @return The base value of HP
     */
    public final int getTotalHitPoints() {
        BaseStat statHP = getStat(TRAIT_VITALITY);
        return statHP.getBaseValue() + statHP.getStaticModifier();
    }

    /**
     * Get the current value of HP.
     *
     * @return The current value of HP
     */
    public final int getCurrentHitPoints() {
        return getStatValue(TRAIT_VITALITY);
    }

    /**
     * Get the base value of Energy.
     *
     * @return The base value of Energy
     */
    public final int getTotalEnergyLevel() {
        BaseStat statEnergy = getStat(TRAIT_ENERGY);
        return statEnergy.getBaseValue() + statEnergy.getStaticModifier();
    }

    /**
     * Get the current value of Energy.
     *
     * @return The current value of Energy
     */
    public final int getCurrentEnergyLevel() {
        return getStatValue(TRAIT_ENERGY);
    }

    /**
     * Get the modified value of Defence Rate.
     *
     * @return The modified value of Defence Rate
     */
    public final int getDefenceRate() {
        return getStatValue(TRAIT_DEFENSE_RATE);
    }

    /**
     * Get the modified value of Attack Rate.
     *
     * @return The modified value of Attack Rate
     */
    public final int getAttackRate() {
        return getStatValue(TRAIT_ATTACK_RATE);
    }

    /**
     * Get the modified value of Physical Power.
     *
     * @return The modified value of Physical Power
     */
    public final int getPhysicalPower() {
        return getStatValue(COMBAT_STAT_PHYSICAL_POWER);
    }

    /**
     * Get the modified value of Magic Power.
     *
     * @return The modified value of Magic Power
     */
    public final int getMagicPower() {
        return getStatValue(COMBAT_STAT_MAGIC_POWER);
    }

    /**
     * Get the modified value of Healing Power.
     *
     * @return The modified value of Healing Power
     */
    public final int getHealingPower() {
        return getStatValue(COMBAT_STAT_HEALING_POWER);
    }

    /**
     * Get all information about this BaseCharacters stats formatted as a table.
     * Takes stat information, stores it as a list of lists of strings, each
     * list of strings constituting a row with the same amount of strings. This list
     * is passed to IOhelper.formatAsTable() which formats the information.
     *
     * @return Stats information in a string formatted as a table
     */
    @Override
    public final String toString() {
        List<List<String>> statInfoRows = new ArrayList<>();

        List<String> statHeader = new ArrayList<>();
        statHeader.add(ANSI_BLUE + "STATISTICS");

        final int STRINGS_IN_EACH_ROW = 5;
        for (int i = 1; i < STRINGS_IN_EACH_ROW; i++) {
            statHeader.add("");
        }

        statInfoRows.add(statHeader);

        // Build a number of rows with one Attribute, one Trait and one CombatStat
        final int ROWS_IN_TABLE_EXCLUDING_HEADER = 4;
        for (int i = 0; i < ROWS_IN_TABLE_EXCLUDING_HEADER; i++) {
            List<String> statInfo = new ArrayList<>();

            // Attribute
            String attributeName = StatsManager.INSTANCE.getAttributeNames().get(i);
            BaseStat attributeFromMember = getStat(attributeName);

            statInfo.add(attributeFromMember.toString());
            statInfo.add((ANSI_WHITE + "|"));

            // Trait
            String traitName = StatsManager.INSTANCE.getTraitNames().get(i);
            BaseStat traitFromMember = getStat(traitName);

            statInfo.add(traitFromMember.toString());
            statInfo.add((ANSI_WHITE + "|"));

            // CombatStat
            String combatStatName = StatsManager.INSTANCE.getCombatStatNames().get(i);
            BaseStat combatStatFromMember = getStat(combatStatName);

            statInfo.add(combatStatFromMember.toString());

            statInfoRows.add(statInfo);
        }

        return IOHelper.formatAsTable(statInfoRows);
    }
}
