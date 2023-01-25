package com.dt180g.project.stats;

import com.dt180g.project.support.Constants;
import com.dt180g.project.support.Randomizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The singleton class responsible for managing stats by storing and
 * providing a public interface for accessing these.
 *
 * @author Samuel Thand
 */
public final class StatsManager implements Constants {

    public static final StatsManager INSTANCE = new StatsManager();
    private final List<String> attributeNames = new ArrayList<>(
    Arrays.asList(
            ATTRIBUTE_STRENGTH,
            ATTRIBUTE_DEXTERITY,
            ATTRIBUTE_INTELLIGENCE,
            ATTRIBUTE_WILLPOWER));
    private final List<String> traitNames = new ArrayList<>(
    Arrays.asList(
            TRAIT_VITALITY,
            TRAIT_ENERGY,
            TRAIT_ATTACK_RATE,
            TRAIT_DEFENSE_RATE));
    private final List<String> combatStatNames = new ArrayList<>(
    Arrays.asList(
            COMBAT_STAT_ACTION_POINTS,
            COMBAT_STAT_PHYSICAL_POWER,
            COMBAT_STAT_MAGIC_POWER,
            COMBAT_STAT_HEALING_POWER));

    /**
     * Singleton constructor.
     */
    private StatsManager() { }

    /**
     * Get a random Attribute name.
     *
     * @return random Attribute name from member attributeNames
     */
    public String getRandomAttributeName() {
        int indexRange = getListIndexRange(attributeNames);
        int randomIndex = Randomizer.INSTANCE.getRandomValue(indexRange);

        return attributeNames.get(randomIndex);
    }

    /**
     * Get a random Trait name.
     *
     * @return random Trait name from member traitNames
     */
    public String getRandomTraitName() {
        int indexRange = getListIndexRange(traitNames);
        int randomIndex = Randomizer.INSTANCE.getRandomValue(indexRange);

        return traitNames.get(randomIndex);
    }

    /**
     * Get the index range of a list.
     *
     * @param list to index into
     * @return index range of list
     */
    private int getListIndexRange(final List<String> list) {
        int indexRange = 0;
        if (list.size() != 0) {
            indexRange = list.size() - 1;
        }

        return indexRange;
    }

    /**
     * Get all Attribute names.
     *
     * @return member attributeNames
     */
    public List<String> getAttributeNames() {
        return attributeNames;
    }

    /**
     * Get all Trait names.
     *
     * @return member traitNames
     */
    public List<String> getTraitNames() {
        return traitNames;
    }

    /**
     * Get all CombatStat names.
     *
     * @return member combatStatNames
     */
    public List<String> getCombatStatNames() {
        return combatStatNames;
    }
}
