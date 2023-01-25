package com.dt180g.project.abilities;

import com.dt180g.project.support.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class abilitiesTests {

    @Test
    public void testWeaponAttackAbility() {
        String abilityName = Constants.ABILITY_WEAPON_ATTACK;
        BaseAbility ability = new WeaponAttack();
        assertAll("Validate that " + abilityName + " ability is correct",
                () -> assertEquals(abilityName, ability.toString()),
                () -> assertEquals(Constants.LOWEST_AP_COST, ability.getActionPointCost()),
                () -> assertEquals(0, ability.getEnergyCost()),
                () -> assertEquals(1, ability.getAmountOfTargets()),
                () -> assertFalse(ability.isMagic()),
                () -> assertFalse(ability.isHeal())
        );
    }

    @Test
    public void testElementalBlastAbility() {
        String magicPhrase = Constants.MAGICAL_PHRASE_1;
        String element = Constants.ELEMENT_FIRE;
        BaseAbility ability = new ElementalBlast(element);
        String expectedInfo = String.format("%s: %s %s", magicPhrase, element, Constants.ABILITY_ELEMENTAL_BLAST);
        assertAll("Validate that Elemental Blast ability is correct",
                () -> assertEquals(expectedInfo, ability.toString()),
                () -> assertEquals(Constants.HIGHEST_AP_COST, ability.getActionPointCost()),
                () -> assertEquals(Constants.HIGH_ENERGY_COST, ability.getEnergyCost()),
                () -> assertEquals(3, ability.getAmountOfTargets()),
                () -> assertTrue(ability.isMagic()),
                () -> assertFalse(ability.isHeal())
        );
    }

    @Test
    public void testElementalBoltAbility() {
        String magicPhrase = Constants.MAGICAL_PHRASE_2;
        String element = Constants.ELEMENT_ICE;
        BaseAbility ability = new ElementalBolt(element);
        String expectedInfo = String.format("%s: %s %s", magicPhrase, element, Constants.ABILITY_ELEMENTAL_BOLT);
        assertAll("Validate that Elemental Bolt ability is correct",
                () -> assertEquals(expectedInfo, ability.toString()),
                () -> assertEquals(Constants.MEDIUM_AP_COST, ability.getActionPointCost()),
                () -> assertEquals(Constants.LOW_ENERGY_COST, ability.getEnergyCost()),
                () -> assertEquals(1, ability.getAmountOfTargets()),
                () -> assertTrue(ability.isMagic()),
                () -> assertFalse(ability.isHeal())
        );
    }

    @Test
    public void testFocusedHealAbility() {
        String abilityName = Constants.ABILITY_FOCUSED_HEAL;
        String magicPhrase = Constants.MAGICAL_PHRASE_4;
        BaseAbility ability = new FocusedHeal();
        String expectedInfo = String.format("%s: %s", magicPhrase, abilityName);
        assertAll("Validate that " + abilityName + " ability is correct",
                () -> assertEquals(expectedInfo, ability.toString()),
                () -> assertEquals(Constants.MEDIUM_AP_COST, ability.getActionPointCost()),
                () -> assertEquals(Constants.LOW_ENERGY_COST, ability.getEnergyCost()),
                () -> assertEquals(1, ability.getAmountOfTargets()),
                () -> assertTrue(ability.isMagic()),
                () -> assertTrue(ability.isHeal())
        );
    }

    @Test
    public void testGroupHealAbility() {
        String abilityName = Constants.ABILITY_GROUP_HEAL;
        String magicPhrase = Constants.MAGICAL_PHRASE_3;
        BaseAbility ability = new GroupHeal();
        String expectedInfo = String.format("%s: %s", magicPhrase, abilityName);
        assertAll("Validate that " + abilityName + " ability is correct",
                () -> assertEquals(expectedInfo, ability.toString()),
                () -> assertEquals(Constants.HIGHEST_AP_COST, ability.getActionPointCost()),
                () -> assertEquals(Constants.HIGH_ENERGY_COST, ability.getEnergyCost()),
                () -> assertEquals(3, ability.getAmountOfTargets()),
                () -> assertTrue(ability.isMagic()),
                () -> assertTrue(ability.isHeal())
        );
    }

    @Test
    public void testFocusedShotAbility() {
        String abilityName = Constants.ABILITY_FOCUSED_SHOT;
        BaseAbility ability = new FocusedShot();
        assertAll("Validate that " + abilityName + " ability is correct",
                () -> assertEquals(abilityName, ability.toString()),
                () -> assertEquals(Constants.MEDIUM_AP_COST, ability.getActionPointCost()),
                () -> assertEquals(Constants.LOW_ENERGY_COST, ability.getEnergyCost()),
                () -> assertEquals(1, ability.getAmountOfTargets()),
                () -> assertFalse(ability.isMagic()),
                () -> assertFalse(ability.isHeal())
        );
    }

    @Test
    public void testSprayOfArrowsAbility() {
        String abilityName = Constants.ABILITY_SPRAY_OF_ARROWS;
        BaseAbility ability = new SprayOfArrows();
        assertAll("Validate that " + abilityName + " ability is correct",
                () -> assertEquals(abilityName, ability.toString()),
                () -> assertEquals(Constants.HIGHEST_AP_COST, ability.getActionPointCost()),
                () -> assertEquals(Constants.HIGH_ENERGY_COST, ability.getEnergyCost()),
                () -> assertEquals(3, ability.getAmountOfTargets()),
                () -> assertFalse(ability.isMagic()),
                () -> assertFalse(ability.isHeal())
        );
    }

    @Test
    public void testHeavyAttackAbility() {
        String abilityName = Constants.ABILITY_HEAVY_ATTACK;
        BaseAbility ability = new HeavyAttack();
        assertAll("Validate that " + abilityName + " ability is correct",
                () -> assertEquals(abilityName, ability.toString()),
                () -> assertEquals(Constants.MEDIUM_AP_COST, ability.getActionPointCost()),
                () -> assertEquals(Constants.LOW_ENERGY_COST, ability.getEnergyCost()),
                () -> assertEquals(1, ability.getAmountOfTargets()),
                () -> assertFalse(ability.isMagic()),
                () -> assertFalse(ability.isHeal())
        );
    }

    @Test
    public void testWhirlwindAbility() {
        String abilityName = Constants.ABILITY_WHIRLWIND;
        BaseAbility ability = new Whirlwind();
        assertAll("Validate that " + abilityName + " ability is correct",
                () -> assertEquals(abilityName, ability.toString()),
                () -> assertEquals(Constants.HIGHEST_AP_COST, ability.getActionPointCost()),
                () -> assertEquals(Constants.HIGH_ENERGY_COST, ability.getEnergyCost()),
                () -> assertEquals(3, ability.getAmountOfTargets()),
                () -> assertFalse(ability.isMagic()),
                () -> assertFalse(ability.isHeal())
        );
    }
}
