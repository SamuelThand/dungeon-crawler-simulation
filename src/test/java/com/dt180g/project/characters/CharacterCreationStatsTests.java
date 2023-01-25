package com.dt180g.project.characters;

import com.dt180g.project.characters.enemies.LichLord;
import com.dt180g.project.characters.enemies.SkeletonArcher;
import com.dt180g.project.characters.enemies.SkeletonMage;
import com.dt180g.project.characters.enemies.SkeletonWarrior;
import com.dt180g.project.characters.heroes.Cleric;
import com.dt180g.project.characters.heroes.Ranger;
import com.dt180g.project.characters.heroes.Warrior;
import com.dt180g.project.characters.heroes.Wizard;

import com.dt180g.project.gear.BaseGear;
import com.dt180g.project.stats.BaseStat;
import com.dt180g.project.support.Constants;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharacterCreationStatsTests {
    private final String heroName = "Ingrid";

    private Map<String, Integer> determineStatValues(Map<String, Integer> stats, Map<String, Integer> gear) {
        gear.forEach((stat, value) -> {
            if (stats.containsKey(stat)) {
                stats.put(stat, stats.get(stat) + value);
            }
        });

        return stats;
    }

    private Map<String, Integer> calcAttributeExpectedValues(List<Integer> baseValues) {
        Map<String, Integer> attributeValues = new HashMap<>();
        attributeValues.put(Constants.ATTRIBUTE_STRENGTH, baseValues.get(0) * Constants.ATTRIBUTE_BASE_VALUE);
        attributeValues.put(Constants.ATTRIBUTE_DEXTERITY, baseValues.get(1) * Constants.ATTRIBUTE_BASE_VALUE);
        attributeValues.put(Constants.ATTRIBUTE_INTELLIGENCE, baseValues.get(2) * Constants.ATTRIBUTE_BASE_VALUE);
        attributeValues.put(Constants.ATTRIBUTE_WILLPOWER, baseValues.get(3) * Constants.ATTRIBUTE_BASE_VALUE);
        return attributeValues;
    }

    private Map<String, Integer> calcTraitExpectedValues(Map<String, Integer> stats) {
        stats.put(Constants.TRAIT_VITALITY, Constants.TRAIT_VITALITY_BASE_VALUE);
        stats.put(Constants.TRAIT_ENERGY, Constants.TRAIT_ENERGY_BASE_VALUE);
        stats.put(Constants.TRAIT_ATTACK_RATE, Constants.TRAIT_ATTACK_RATE_BASE_VALUE);
        stats.put(Constants.TRAIT_DEFENSE_RATE, Constants.TRAIT_DEFENCE_RATE_BASE_VALUE);
        return stats;
    }

    private Map<String, Integer> calcCombatStatExpectedValues(Map<String, Integer> stats) {
        double attackValue = stats.get(Constants.TRAIT_ATTACK_RATE) * Constants.COMBAT_STAT_MULTIPLIER;

        double strengthValue = stats.get(Constants.ATTRIBUTE_STRENGTH) * Constants.COMBAT_STAT_MULTIPLIER;
        double dexterityValue = stats.get(Constants.ATTRIBUTE_DEXTERITY) * Constants.COMBAT_STAT_MULTIPLIER;
        double intelligenceValue = stats.get(Constants.ATTRIBUTE_INTELLIGENCE) * Constants.COMBAT_STAT_MULTIPLIER;
        double willpowerValue = stats.get(Constants.ATTRIBUTE_WILLPOWER) * Constants.COMBAT_STAT_MULTIPLIER;

        stats.put(Constants.COMBAT_STAT_ACTION_POINTS, (int) Math.round(dexterityValue + attackValue));
        stats.put(Constants.COMBAT_STAT_PHYSICAL_POWER, (int) Math.round(strengthValue + attackValue));
        stats.put(Constants.COMBAT_STAT_MAGIC_POWER, (int) Math.round(intelligenceValue + attackValue));
        stats.put(Constants.COMBAT_STAT_HEALING_POWER, (int) Math.round(willpowerValue + attackValue));

        return stats;
    }

    private Map<String, Integer> calcGearStats(List<? extends BaseGear> gear) {
        Map<String, Integer> gearStats = new HashMap<>();
        for (BaseGear item : gear) {
            BaseStat stat = item.getStat();
            int newValue = (gearStats.containsKey(stat.getStatName()))
                    ? gearStats.get(stat.getStatName()) + stat.getModifiedValue() : stat.getModifiedValue();
            gearStats.put(stat.getStatName(), newValue);
        }
        return gearStats;
    }

    private Map<String, Integer> calcAllStats(BaseCharacter character, List<Integer> attributeValues) {
        CharacterEquipment equipment = character.getEquipment();

        Map<String, Integer> stats = determineStatValues(
                calcAttributeExpectedValues(attributeValues), calcGearStats(equipment.getWeapons()));

        determineStatValues(calcTraitExpectedValues(stats), calcGearStats(equipment.getArmorPieces()));

        calcCombatStatExpectedValues(stats);
        return stats;
    }

    private void runAttributeAssertions(CharacterStats stats, Map<String, Integer> expStats) {
        assertAll("Validate that starting Attributes are correct",
                () -> assertEquals(expStats.get(Constants.ATTRIBUTE_STRENGTH), stats.getStatValue(Constants.ATTRIBUTE_STRENGTH)),
                () -> assertEquals(expStats.get(Constants.ATTRIBUTE_DEXTERITY), stats.getStatValue(Constants.ATTRIBUTE_DEXTERITY)),
                () -> assertEquals(expStats.get(Constants.ATTRIBUTE_INTELLIGENCE), stats.getStatValue(Constants.ATTRIBUTE_INTELLIGENCE)),
                () -> assertEquals(expStats.get(Constants.ATTRIBUTE_WILLPOWER), stats.getStatValue(Constants.ATTRIBUTE_WILLPOWER))
        );
    }

    private void runTraitAssertions(CharacterStats stats, Map<String, Integer> expStats) {
        assertAll("Validate that starting Stats are correct",
                () -> assertEquals(expStats.get(Constants.TRAIT_VITALITY), stats.getStatValue(Constants.TRAIT_VITALITY)),
                () -> assertEquals(expStats.get(Constants.TRAIT_ENERGY), stats.getStatValue(Constants.TRAIT_ENERGY)),
                () -> assertEquals(expStats.get(Constants.TRAIT_ATTACK_RATE), stats.getStatValue(Constants.TRAIT_ATTACK_RATE)),
                () -> assertEquals(expStats.get(Constants.TRAIT_DEFENSE_RATE), stats.getStatValue(Constants.TRAIT_DEFENSE_RATE))
        );
    }

    private void runCombatStatAssertions(CharacterStats stats, Map<String, Integer> expStats) {
        assertAll("Validate that starting Combat Stats are correct",
                () -> assertEquals(expStats.get(Constants.COMBAT_STAT_ACTION_POINTS), stats.getStatValue(Constants.COMBAT_STAT_ACTION_POINTS)),
                () -> assertEquals(expStats.get(Constants.COMBAT_STAT_PHYSICAL_POWER), stats.getStatValue(Constants.COMBAT_STAT_PHYSICAL_POWER)),
                () -> assertEquals(expStats.get(Constants.COMBAT_STAT_MAGIC_POWER), stats.getStatValue(Constants.COMBAT_STAT_MAGIC_POWER)),
                () -> assertEquals(expStats.get(Constants.COMBAT_STAT_HEALING_POWER), stats.getStatValue(Constants.COMBAT_STAT_HEALING_POWER))
        );
    }

    @Test
    public void testSkeletonWarriorStats() {
        BaseCharacter character = new SkeletonWarrior(0);
        Map<String, Integer> stats = calcAllStats(character, Constants.ATTRIBUTE_VALUES_SKELETON_WARRIOR);
        assertAll("Validate creation stats of Skeleton Warrior",
                () -> runAttributeAssertions(character.getCharacterStats(), stats),
                () -> runTraitAssertions(character.getCharacterStats(), stats),
                () -> runCombatStatAssertions(character.getCharacterStats(), stats)
        );
    }

    @Test
    public void testSkeletonArcherStats() {
        BaseCharacter character = new SkeletonArcher(0);
        Map<String, Integer> stats = calcAllStats(character, Constants.ATTRIBUTE_VALUES_SKELETON_ARCHER);
        assertAll("Validate creation stats of Skeleton Archer",
                () -> runAttributeAssertions(character.getCharacterStats(), stats),
                () -> runTraitAssertions(character.getCharacterStats(), stats),
                () -> runCombatStatAssertions(character.getCharacterStats(), stats)
        );
    }

    @Test
    public void testSkeletonMageStats() {
        BaseCharacter character = new SkeletonMage(0);
        Map<String, Integer> stats = calcAllStats(character, Constants.ATTRIBUTE_VALUES_SKELETON_MAGE);
        assertAll("Validate creation stats of Skeleton Mage",
                () -> runAttributeAssertions(character.getCharacterStats(), stats),
                () -> runTraitAssertions(character.getCharacterStats(), stats),
                () -> runCombatStatAssertions(character.getCharacterStats(), stats)
        );
    }

    @Test
    public void testLichLordStats() {
        BaseCharacter character = new LichLord();
        Map<String, Integer> stats = calcAllStats(character, Constants.ATTRIBUTE_VALUES_LICH_LORD);
        int baseHealth = stats.get(Constants.TRAIT_VITALITY);
        stats.put(Constants.TRAIT_VITALITY, baseHealth + (baseHealth * Constants.BOSS_HEALTH_MULTIPLIER));
        assertAll("Validate creation stats of Lich Lord",
                () -> runAttributeAssertions(character.getCharacterStats(), stats),
                () -> runTraitAssertions(character.getCharacterStats(), stats),
                () -> runCombatStatAssertions(character.getCharacterStats(), stats)
        );
    }

    @Test
    public void testWarriorStats() {
        BaseCharacter character = new Warrior(heroName);
        Map<String, Integer> stats = calcAllStats(character, Constants.ATTRIBUTE_VALUES_WARRIOR_HERO);
        assertAll("Validate creation stats of Warrior hero",
                () -> runAttributeAssertions(character.getCharacterStats(), stats),
                () -> runTraitAssertions(character.getCharacterStats(), stats),
                () -> runCombatStatAssertions(character.getCharacterStats(), stats)
        );
    }

    @Test
    public void testRangerStats() {
        BaseCharacter character = new Ranger(heroName);
        Map<String, Integer> stats = calcAllStats(character, Constants.ATTRIBUTE_VALUES_RANGER_HERO);
        assertAll("Validate creation stats of Ranger hero",
                () -> runAttributeAssertions(character.getCharacterStats(), stats),
                () -> runTraitAssertions(character.getCharacterStats(), stats),
                () -> runCombatStatAssertions(character.getCharacterStats(), stats)
        );
    }

    @Test
    public void testWizardStats() {
        BaseCharacter character = new Wizard(heroName);
        Map<String, Integer> stats = calcAllStats(character, Constants.ATTRIBUTE_VALUES_WIZARD_HERO);
        assertAll("Validate creation stats of Wizard hero",
                () -> runAttributeAssertions(character.getCharacterStats(), stats),
                () -> runTraitAssertions(character.getCharacterStats(), stats),
                () -> runCombatStatAssertions(character.getCharacterStats(), stats)
        );
    }

    @Test
    public void testClericStats() {
        BaseCharacter character = new Cleric(heroName);
        Map<String, Integer> stats = calcAllStats(character, Constants.ATTRIBUTE_VALUES_CLERIC_HERO);
        assertAll("Validate creation stats of Cleric hero",
                () -> runAttributeAssertions(character.getCharacterStats(), stats),
                () -> runTraitAssertions(character.getCharacterStats(), stats),
                () -> runCombatStatAssertions(character.getCharacterStats(), stats)
        );
    }
}
