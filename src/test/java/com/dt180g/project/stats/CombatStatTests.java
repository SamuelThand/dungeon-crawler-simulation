package com.dt180g.project.stats;

import com.dt180g.project.support.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CombatStatTests {
    private final BaseStat STAT_RELIANCE = new Trait(Constants.TRAIT_ATTACK_RATE, Constants.TRAIT_ATTACK_RATE_BASE_VALUE);
    private final int baseModifier = -5;

    private void runAsserts(String statName, BaseStat attributeReliance, int extraModifier) {
        BaseStat combatStat = new CombatStat(statName, attributeReliance, STAT_RELIANCE);
        combatStat.adjustStaticModifier(baseModifier);
        combatStat.adjustDynamicModifier(extraModifier);

        double attrValue = attributeReliance.getModifiedValue() * Constants.COMBAT_STAT_MULTIPLIER;
        double statValue = STAT_RELIANCE.getModifiedValue() * Constants.COMBAT_STAT_MULTIPLIER;
        int correctBaseValue = (int) Math.round(attrValue + statValue);
        int correctModifiedValue = correctBaseValue + (baseModifier + extraModifier);

        assertAll("Validate that stat values are correct",
                () -> assertEquals(statName, combatStat.getStatName()),
                () -> assertEquals(correctBaseValue, combatStat.getBaseValue()),
                () -> assertEquals(baseModifier + extraModifier, combatStat.getTotalModifier()),
                () -> assertEquals(correctModifiedValue, combatStat.getModifiedValue())
        );
    }

    private void testResetModifier(String statName, BaseStat attributeReliance) {
        BaseStat combatStat = new CombatStat(statName, attributeReliance, STAT_RELIANCE);
        int withoutDynamicModifier = combatStat.getModifiedValue();
        combatStat.adjustDynamicModifier(baseModifier);
        combatStat.resetDynamicModifier();
        assertEquals(withoutDynamicModifier, combatStat.getModifiedValue());
    }

    /* ACTION POINTS TESTS */

    @Test
    public void testCombatStatActionPoints() {
        BaseStat attributeReliance = new Attribute(Constants.ATTRIBUTE_DEXTERITY, 80);
        runAsserts(Constants.COMBAT_STAT_ACTION_POINTS, attributeReliance, 0);
    }

    @Test
    public void testCombatStatActionPointsExtraModifier() {
        BaseStat attributeReliance = new Attribute(Constants.ATTRIBUTE_DEXTERITY, 80);
        runAsserts(Constants.COMBAT_STAT_ACTION_POINTS, attributeReliance, 3);
    }

    @Test
    public void testCombatStatActionPointsResetModifier() {
        BaseStat attributeReliance = new Attribute(Constants.ATTRIBUTE_DEXTERITY, 80);
        testResetModifier(Constants.COMBAT_STAT_ACTION_POINTS, attributeReliance);
    }

    /* PHYSICAL POWER TESTS */

    @Test
    public void testCombatStatPhysicalPower() {
        BaseStat attributeReliance = new Attribute(Constants.ATTRIBUTE_STRENGTH, 80);
        runAsserts(Constants.COMBAT_STAT_PHYSICAL_POWER, attributeReliance, 0);
    }

    @Test
    public void testCombatStatPhysicalPowerExtraModifier() {
        BaseStat attributeReliance = new Attribute(Constants.ATTRIBUTE_STRENGTH, 80);
        runAsserts(Constants.COMBAT_STAT_PHYSICAL_POWER, attributeReliance, 5);
    }

    @Test
    public void testCombatStatPhysicalPowerResetModifier() {
        BaseStat attributeReliance = new Attribute(Constants.ATTRIBUTE_STRENGTH, 80);
        testResetModifier(Constants.COMBAT_STAT_PHYSICAL_POWER, attributeReliance);
    }

    /* MAGIC POWER TESTS */

    @Test
    public void testCombatStatMagicPower() {
        BaseStat attributeReliance = new Attribute(Constants.ATTRIBUTE_INTELLIGENCE, 80);
        runAsserts(Constants.COMBAT_STAT_MAGIC_POWER, attributeReliance, 0);
    }

    @Test
    public void testCombatStatMagicPowerExtraModifier() {
        BaseStat attributeReliance = new Attribute(Constants.ATTRIBUTE_INTELLIGENCE, 80);
        runAsserts(Constants.COMBAT_STAT_MAGIC_POWER, attributeReliance, 7);
    }

    @Test
    public void testCombatStatMagicPowerResetModifier() {
        BaseStat attributeReliance = new Attribute(Constants.ATTRIBUTE_INTELLIGENCE, 80);
        testResetModifier(Constants.COMBAT_STAT_MAGIC_POWER, attributeReliance);
    }

    /* HEALING POWER TESTS */

    @Test
    public void testCombatStatHealingPower() {
        BaseStat attributeReliance = new Attribute(Constants.ATTRIBUTE_WILLPOWER, 80);
        runAsserts(Constants.COMBAT_STAT_HEALING_POWER, attributeReliance, 0);
    }

    @Test
    public void testCombatStatHealingPowerExtraModifier() {
        BaseStat attributeReliance = new Attribute(Constants.ATTRIBUTE_WILLPOWER, 80);
        runAsserts(Constants.COMBAT_STAT_HEALING_POWER, attributeReliance, 9);
    }

    @Test
    public void testCombatStatHealingPowerResetModifier() {
        BaseStat attributeReliance = new Attribute(Constants.ATTRIBUTE_WILLPOWER, 80);
        testResetModifier(Constants.COMBAT_STAT_HEALING_POWER, attributeReliance);
    }
}
