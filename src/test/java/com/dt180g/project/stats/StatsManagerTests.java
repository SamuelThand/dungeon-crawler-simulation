package com.dt180g.project.stats;

import com.dt180g.project.support.Constants;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatsManagerTests {
    private final List<String> attributeNames = Arrays.asList(
            Constants.ATTRIBUTE_STRENGTH, Constants.ATTRIBUTE_DEXTERITY,
            Constants.ATTRIBUTE_INTELLIGENCE, Constants.ATTRIBUTE_WILLPOWER);

    private final List<String> statNames = Arrays.asList(
            Constants.TRAIT_VITALITY, Constants.TRAIT_ENERGY,
            Constants.TRAIT_ATTACK_RATE, Constants.TRAIT_DEFENSE_RATE);

    private final List<String> combatStatNames = Arrays.asList(
            Constants.COMBAT_STAT_ACTION_POINTS, Constants.COMBAT_STAT_PHYSICAL_POWER,
            Constants.COMBAT_STAT_MAGIC_POWER, Constants.COMBAT_STAT_HEALING_POWER);

    @Test
    public void testSingletonAccess() {
        assertThrows(IllegalAccessException.class, StatsManager.class::newInstance);
    }

    private void runAsserts(List<String> stats, List<String> controlList) {
        assertAll("Validate that manager correctly returns stat names",
                () -> assertEquals(controlList.size(), stats.size()),
                () -> assertTrue(stats.containsAll(controlList))
        );
    }

    @Test
    public void testManagerAttributeNames() {
        runAsserts(StatsManager.INSTANCE.getAttributeNames(), attributeNames);
    }

    @Test
    public void testManagerStatName() {
        runAsserts(StatsManager.INSTANCE.getTraitNames(), statNames);
    }

    @Test
    public void testManagerCombatStatName() {
        runAsserts(StatsManager.INSTANCE.getCombatStatNames(), combatStatNames);
    }

    @Test
    public void testManagerRandomAttributeName() {
        assertTrue(attributeNames.contains(StatsManager.INSTANCE.getRandomAttributeName()));
    }

    @Test
    public void testManagerRandomStatName() {
        assertTrue(statNames.contains(StatsManager.INSTANCE.getRandomTraitName()));
    }
}
