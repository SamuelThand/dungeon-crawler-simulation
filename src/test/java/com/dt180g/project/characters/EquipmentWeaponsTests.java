package com.dt180g.project.characters;

import com.dt180g.project.gear.Weapon;

import com.dt180g.project.support.Constants;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EquipmentWeaponsTests {
    private List<Weapon> weapons = new ArrayList<>();
    private final int weaponDamage = 7;

    public EquipmentWeaponsTests() {
        for (String wield : Arrays.asList("Two Handed Melee", "One Handed Melee", "One Handed Ranged")) {
            Map<String, String> details = new HashMap<>();
            details.put("type", Constants.WEAPON_AXE);
            details.put("name", "Practice Weapon");
            details.put("wield", wield);
            details.put("damage", Integer.toString(weaponDamage));
            details.put("restriction", "Warrior");
            weapons.add(new Weapon(details));
        }
    }

    private void runWeaponAsserts(int expEmptySlots, int expDamage, CharacterEquipment equipment) {
        assertAll("Validate that equipment reports correct weapon values",
                () -> assertEquals(expEmptySlots, equipment.amountOfEmptyWeaponSlots()),
                () -> assertEquals(expDamage, equipment.getTotalWeaponDamage())
        );
    }

    @Test
    public void testEmptyWeaponSlots() {
        runWeaponAsserts(2, 0, new CharacterEquipment());
    }

    @Test
    public void testSingleTwoHandedWeapon() {
        CharacterEquipment equipment = new CharacterEquipment();
        Weapon weapon = weapons.get(0);
        equipment.addWeapon(weapon);
        runWeaponAsserts(0, weaponDamage, equipment);
    }

    @Test
    public void testDualTwoHandedWeapon() {
        CharacterEquipment equipment = new CharacterEquipment();
        assertAll("Validate equipment weapon slots",
                () -> assertTrue(equipment.addWeapon(weapons.get(0))),  // should be two hander
                () -> assertFalse(equipment.addWeapon(weapons.get(0)))
        );
    }

    @Test
    public void testSingleOneHandedWeapon() {
        CharacterEquipment equipment = new CharacterEquipment();
        Weapon weapon = weapons.get(1);
        equipment.addWeapon(weapon);
        runWeaponAsserts(1, weaponDamage, equipment);
    }

    @Test
    public void testDualOneHandedWeapon() {
        CharacterEquipment equipment = new CharacterEquipment();
        List<Weapon> oneHanders = weapons.subList(1, weapons.size());
        oneHanders.forEach(equipment::addWeapon);
        runWeaponAsserts(0, weaponDamage * 2, equipment);
    }

    @Test
    public void testDualMixWieldWeapon() {
        CharacterEquipment equipment = new CharacterEquipment();
        assertAll("Validate equipment weapon slots",
                () -> assertTrue(equipment.addWeapon(weapons.get(0))),  // should be two hander
                () -> assertFalse(equipment.addWeapon(weapons.get(1)))  // should be one hander
        );
    }
}
