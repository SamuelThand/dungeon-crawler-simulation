package com.dt180g.project.gear;

import com.dt180g.project.characters.heroes.Cleric;
import com.dt180g.project.characters.heroes.Ranger;
import com.dt180g.project.characters.heroes.Warrior;
import com.dt180g.project.characters.heroes.Wizard;
import com.dt180g.project.stats.BaseStat;
import com.dt180g.project.stats.Trait;
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

public class ArmorTests {
    private final Armor armor;
    private final String type = Constants.ARMOR_CHEST;
    private final String name = "Practice Armor";
    private final String material = "Plastic";
    private final int protection = 10;

    public ArmorTests() {
        Map<String, String> details = new HashMap<>();
        details.put("type", type);
        details.put("name", name);
        details.put("material", material);
        details.put("protection", Integer.toString(protection));
        details.put("restriction", String.join(",",
                Arrays.asList(Constants.HERO_CLERIC, Constants.HERO_RANGER)));
        armor = new Armor(details);
    }

    @Test
    public void validateBaseClassAbstraction() {  // super class should be abstract
        assertThrows(InstantiationException.class, BaseGear.class::newInstance);
    }

    @Test
    public void testArmorProtection() {
        assertEquals(protection, armor.getProtection());
    }

    @Test
    public void testArmorName() {
        String expectedName = String.format("%s of %s", name, armor.getStat().getStatName());
        assertEquals(expectedName, armor.toString());
    }

    @Test
    public void testArmorType() { assertEquals(type, armor.getType()); }

    @Test
    public void testArmorMaterial() {
        assertEquals(material, armor.getMaterial());
    }

    @Test
    public void testArmorRestrictions() {
        assertAll("Validate that armor restrictions are correct",
                () -> assertTrue(armor.checkClassRestriction(Cleric.class)),
                () -> assertTrue(armor.checkClassRestriction(Ranger.class)),
                () -> assertFalse(armor.checkClassRestriction(Warrior.class)),
                () -> assertFalse(armor.checkClassRestriction(Wizard.class))
        );
    }

    @Test
    public void testArmorStat() {
        List<String> statNames = Arrays.asList(
                Constants.TRAIT_VITALITY, Constants.TRAIT_ENERGY,
                Constants.TRAIT_ATTACK_RATE, Constants.TRAIT_DEFENSE_RATE);

        BaseStat stat = armor.getStat();
        assertAll("Validate that weapon attribute is correct",
                () -> assertTrue(stat instanceof Trait),
                () -> assertTrue(statNames.contains(stat.getStatName())),
                () -> assertTrue(stat.getModifiedValue() > 0),
                () -> assertTrue(stat.getModifiedValue() < 16)
        );
    }
}
