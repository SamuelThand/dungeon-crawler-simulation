package com.dt180g.project.gear;

import com.dt180g.project.characters.heroes.Cleric;
import com.dt180g.project.characters.heroes.Ranger;
import com.dt180g.project.characters.heroes.Warrior;
import com.dt180g.project.characters.heroes.Wizard;
import com.dt180g.project.stats.Attribute;
import com.dt180g.project.stats.BaseStat;
import com.dt180g.project.support.Constants;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeaponTests {
    private final Weapon weapon;
    private final String type = Constants.WEAPON_AXE;
    private final String name = "Practice Weapon";
    private final String wield = "Two Handed Melee";
    private final int damage = 10;

    public WeaponTests() {
        Map<String, String> details = new HashMap<>();
        details.put("type", type);
        details.put("name", name);
        details.put("wield", wield);
        details.put("damage", Integer.toString(damage));
        details.put("restriction", String.join(",",
                Arrays.asList(Constants.HERO_WARRIOR, Constants.HERO_WIZARD)));
        weapon = new Weapon(details);
    }

    @Test
    public void validateBaseClassAbstraction() {  // super class should be abstract
        assertThrows(InstantiationException.class, BaseGear.class::newInstance);
    }

    @Test
    public void testWeaponDamage() {
        assertEquals(damage, weapon.getDamage());
    }

    @Test
    public void testWeaponName() {
        String expectedName = String.format("%s of %s", name, weapon.getStat().getStatName());
        assertEquals(expectedName, weapon.toString());
    }

    @Test
    public void testWeaponType() {
        assertEquals(type, weapon.getType());
    }

    @Test
    public void testWeaponWield() {
        assertAll("Validate that weapon wield is correct",
                () -> assertTrue(weapon.isTwoHanded()),
                () -> assertEquals(wield, weapon.getWield())
        );
    }

    @Test
    public void testWeaponRestrictions() {
        assertAll("Validate that weapon restrictions are correct",
                () -> assertTrue(weapon.checkClassRestriction(Warrior.class)),
                () -> assertTrue(weapon.checkClassRestriction(Wizard.class)),
                () -> assertFalse(weapon.checkClassRestriction(Ranger.class)),
                () -> assertFalse(weapon.checkClassRestriction(Cleric.class))
        );
    }

    @Test
    public void testWeaponAttribute() {
        List<String> attributeNames = Arrays.asList(
                Constants.ATTRIBUTE_STRENGTH, Constants.ATTRIBUTE_DEXTERITY,
                Constants.ATTRIBUTE_INTELLIGENCE, Constants.ATTRIBUTE_WILLPOWER);

        BaseStat attribute = weapon.getStat();
        assertAll("Validate that weapon attribute is correct",
                () -> assertTrue(attribute instanceof Attribute),
                () -> assertTrue(attributeNames.contains(attribute.getStatName())),
                () -> assertTrue(attribute.getModifiedValue() > 0),
                () -> assertTrue(attribute.getModifiedValue() < 11)
        );
    }
}
