package com.dt180g.project.gear;

import com.dt180g.project.characters.heroes.Cleric;
import com.dt180g.project.characters.heroes.Ranger;
import com.dt180g.project.characters.heroes.Warrior;
import com.dt180g.project.characters.heroes.Wizard;
import com.dt180g.project.support.Constants;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GearManagerTests {
    private final List<String> weaponTypes = Arrays.asList(
            Constants.WEAPON_AXE, Constants.WEAPON_BOW, Constants.WEAPON_CROSSBOW,
            Constants.WEAPON_ORB, Constants.WEAPON_STAFF, Constants.WEAPON_SWORD,
            Constants.WEAPON_SHIELD, Constants.WEAPON_TOME, Constants.WEAPON_WAND);

    @Test
    public void testSingletonAccess() {
        assertThrows(IllegalAccessException.class, GearManager.class::newInstance);
    }

    /* TESTS FOR ARMOR RETRIEVAL */

    @Test
    public void testManagerGetAllArmorPieces() {
        List<String> armorTypes = Arrays.asList(Constants.ARMOR_CHEST, Constants.ARMOR_FEET,
                Constants.ARMOR_HANDS, Constants.ARMOR_HEAD, Constants.ARMOR_LEGS);

        Map<String, List<Armor>> allPieces = GearManager.INSTANCE.getAllMappedArmorPieces();
        assertAll("Validate that the manager provides all armor correctly",
                () -> assertTrue(allPieces.size() > 0),
                () -> armorTypes.forEach(type -> assertTrue(allPieces.containsKey(type)))
        );
    }

    @Test
    public void testManagerRandomArmorOfType() {
        String armorType = Constants.ARMOR_CHEST;
        Class<?> classRestriction = Warrior.class;
        Armor armorPiece = GearManager.INSTANCE.getRandomArmorOfType(armorType, classRestriction);
        assertAll("Validate that the manager returns random weapon correctly",
                () -> assertEquals(armorType, armorPiece.getType()),
                () -> assertTrue(armorPiece.checkClassRestriction(classRestriction))
        );
    }

    private void runAssertsForRestrictions(List<? extends BaseGear> targetList, Class<?> classRestriction) {
        assertAll("Validate that we get gear relevant to class",
                () -> assertTrue(targetList.size() > 0),
                () -> targetList.forEach(piece -> assertTrue(piece.checkClassRestriction(classRestriction)))
        );
    }

    @Test
    public void testManagerAllArmorForClericRestriction() {
        runAssertsForRestrictions(GearManager.INSTANCE.getAllArmorForRestriction(Cleric.class), Cleric.class);
    }

    @Test
    public void testManagerAllArmorForRangerRestriction() {
        runAssertsForRestrictions(GearManager.INSTANCE.getAllArmorForRestriction(Ranger.class), Ranger.class);
    }

    @Test
    public void testManagerAllArmorForWarriorRestriction() {
        runAssertsForRestrictions(GearManager.INSTANCE.getAllArmorForRestriction(Warrior.class), Warrior.class);
    }

    @Test
    public void testManagerAllArmorForWizardRestriction() {
        runAssertsForRestrictions(GearManager.INSTANCE.getAllArmorForRestriction(Wizard.class), Wizard.class);
    }

    /* TESTS FOR WEAPON RETRIEVAL */
    @Test
    public void testManagerGetAllWeapons() {
        Map<String, List<Weapon>> allWeapons = GearManager.INSTANCE.getAllMappedWeapons();
        assertAll("Validate that the manager provides all armor correctly",
                () -> assertTrue(allWeapons.size() > 0),
                () -> weaponTypes.forEach(type -> assertTrue(allWeapons.containsKey(type)))
        );
    }

    @Test
    public void testManagerGetRandomWeapon() {
        Class<?> classRestriction = Wizard.class;
        assertTrue(GearManager.INSTANCE.getRandomWeapon(classRestriction).checkClassRestriction(classRestriction));
    }

    @Test
    public void testManagerGetRandomWeaponOfTypes() {
        List<String> weaponTypes = Arrays.asList(Constants.WEAPON_SWORD, Constants.WEAPON_BOW);
        Weapon weapon = GearManager.INSTANCE.getRandomWeapon(weaponTypes);
        assertTrue(weaponTypes.contains(weapon.getType()));
    }

    @Test
    public void testManagerWeaponsOfType() {
        String weaponType = Constants.WEAPON_BOW;
        List<Weapon> weapons = GearManager.INSTANCE.getWeaponsOfType(weaponType);
        assertAll("Validate that the manager provides all weapons of type correctly",
                () -> assertTrue(weapons.size() > 0),
                () -> weapons.forEach(weapon -> assertEquals(weaponType, weapon.getType()))
        );
    }

    @Test
    public void testManagerGetRandomOneHandedWeapon() {
        Weapon weapon = GearManager.INSTANCE.getRandomOneHandedWeapon(Warrior.class);
        assertFalse(weapon.isTwoHanded());
    }

    @Test
    public void testManagerGetRandomOneHandedWeaponOfTypes() {
        List<String> weaponTypes = Arrays.asList(Constants.WEAPON_SHIELD, Constants.WEAPON_SWORD);
        Weapon weapon = GearManager.INSTANCE.getRandomOneHandedWeapon(weaponTypes);
        assertAll("Validate that the manager provides all weapons of type correctly",
                () -> assertFalse(weapon.isTwoHanded()),
                () -> assertTrue(weaponTypes.contains(weapon.getType()))
        );
    }
}
