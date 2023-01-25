package com.dt180g.project.characters;

import com.dt180g.project.gear.Armor;

import com.dt180g.project.support.Constants;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EquipmentArmorTests {
    private final Map<String, Armor> armorPieces = new HashMap<>();
    private final int armorProtection = 10;

    public EquipmentArmorTests() {
        for (String type : Arrays.asList(Constants.ARMOR_CHEST, Constants.ARMOR_FEET,
                Constants.ARMOR_HANDS, Constants.ARMOR_HEAD, Constants.ARMOR_LEGS)) {
            Map<String, String> details = new HashMap<>();
            details.put("type", Constants.WEAPON_AXE);
            details.put("name", "Practice Armor");
            details.put("material", "Plastic");
            details.put("protection", Integer.toString(armorProtection));
            details.put("restriction", "Warrior");
            armorPieces.put(type, new Armor(details));
        }
    }


    private void runArmorAsserts(int expEmptySlots, int expProtection, CharacterEquipment equipment) {
        assertAll("Validate that equipment reports correct armor values",
                () -> assertEquals(expEmptySlots, equipment.amountOfEmptyArmorSlots()),
                () -> assertEquals(expProtection, equipment.getTotalArmorProtection())
        );
    }

    @Test
    public void testFullArmorSet() {
        CharacterEquipment equipment = new CharacterEquipment();
        armorPieces.forEach(equipment::addArmorPiece);
        runArmorAsserts(0,armorProtection * armorPieces.size(), equipment);
    }

    @Test
    public void testPartialArmorSet() {
        CharacterEquipment equipment = new CharacterEquipment();
        List<String> types = Arrays.asList(Constants.ARMOR_LEGS, Constants.ARMOR_HEAD, Constants.ARMOR_CHEST);
        for (String type : types)
            equipment.addArmorPiece(type, armorPieces.get(type));

        runArmorAsserts(armorPieces.size() - types.size(),armorProtection * types.size(), equipment);
    }

    @Test
    public void testMaxArmorSlots() {
        CharacterEquipment equipment = new CharacterEquipment();
        armorPieces.forEach(equipment::addArmorPiece);
        assertAll("Make sure no more pieces can be added to full armor set",
                () -> assertEquals(0, equipment.amountOfEmptyArmorSlots()),
                () -> assertFalse(equipment.addArmorPiece("TEST", armorPieces.get(0)))
        );
    }

    /* TEST FOR DUPLICATES */

    private void runDuplicateAsserts(String armorType) {
        Armor armorPiece = armorPieces.get(armorType);
        CharacterEquipment equipment = new CharacterEquipment();
        assertAll("Validate that no two armor pieces of same type can be added",
                () -> assertTrue(equipment.addArmorPiece(armorType, armorPiece)),
                () -> assertEquals(armorPieces.size() - 1, equipment.amountOfEmptyArmorSlots()),
                () -> assertFalse(equipment.addArmorPiece(armorType, armorPiece))
        );
    }

    @Test
    public void testDuplicateChestArmor() {
        runDuplicateAsserts(Constants.ARMOR_CHEST);
    }

    @Test
    public void testDuplicateFeetArmor() {
        runDuplicateAsserts(Constants.ARMOR_FEET);
    }

    @Test
    public void testDuplicateHandsArmor() {
        runDuplicateAsserts(Constants.ARMOR_HANDS);
    }

    @Test
    public void testDuplicateHeadArmor() {
        runDuplicateAsserts(Constants.ARMOR_HEAD);
    }

    @Test
    public void testDuplicateLegsArmor() {
        runDuplicateAsserts(Constants.ARMOR_LEGS);
    }
}
