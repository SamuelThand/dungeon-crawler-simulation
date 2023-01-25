package com.dt180g.project.characters;

import com.dt180g.project.abilities.*;

import com.dt180g.project.characters.enemies.LichLord;
import com.dt180g.project.characters.enemies.SkeletonArcher;
import com.dt180g.project.characters.enemies.SkeletonMage;
import com.dt180g.project.characters.enemies.SkeletonWarrior;
import com.dt180g.project.characters.heroes.Cleric;
import com.dt180g.project.characters.heroes.Ranger;
import com.dt180g.project.characters.heroes.Warrior;
import com.dt180g.project.characters.heroes.Wizard;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharacterCreationTests {
    private final String heroName = "Fredde";

    private List<Class<? extends BaseAbility>> getAbilitiesAsTypes(BaseCharacter character) {
        List<Class<? extends BaseAbility>> output = new ArrayList<>();
        for (BaseAbility ability : character.getAbilities()) {
            output.add(ability.getClass());
        }
        return output;
    }

    @Test
    public void validateBaseClassAbstraction() {  // super class should be abstract
        assertThrows(InstantiationException.class, BaseCharacter.class::newInstance);
    }

    /* MAKE SURE CHARACTERS ARE CREATED WITH CORRECT NAME & ABILITIES */

    private void runAbilitiesAssertion(BaseCharacter enemy, List<Class<? extends BaseAbility>> abilityTypes, int expectedAmount) {
        List<Class<? extends BaseAbility>> abilities = getAbilitiesAsTypes(enemy);

        assertAll("Validate that enemy has the correct abilities",
                () -> assertEquals(expectedAmount, abilities.size()),
                () -> assertTrue(abilities.containsAll(abilityTypes))
        );
    }

    @Test
    public void testSkeletonWarriorCreation() {
        BaseCharacter character = new SkeletonWarrior(0);
        assertAll("Validate creation of Skeleton Warrior",
                () -> assertEquals("Skeleton Warrior 0", character.getCharacterName()),
                () -> runAbilitiesAssertion(character, Arrays.asList(
                        WeaponAttack.class, HeavyAttack.class, Whirlwind.class), 3)
        );
    }

    @Test
    public void testSkeletonArcherCreation() {
        BaseCharacter character = new SkeletonArcher(0);
        assertAll("Validate creation of Skeleton Archer",
                () -> assertEquals("Skeleton Archer 0", character.getCharacterName()),
                () -> runAbilitiesAssertion(character, Arrays.asList(
                        WeaponAttack.class, FocusedShot.class, SprayOfArrows.class), 3)
        );
    }

    @Test
    public void testSkeletonMageCreation() {
        BaseCharacter character = new SkeletonMage(0);
        assertAll("Validate creation of Skeleton Mage",
                () -> assertEquals("Skeleton Mage 0", character.getCharacterName()),
                () -> runAbilitiesAssertion(character, Arrays.asList(
                        WeaponAttack.class, ElementalBolt.class, ElementalBlast.class), 7)
        );
    }

    @Test
    public void testLichLordCreation() {
        BaseCharacter character = new LichLord();
        assertAll("Validate creation of Lich Lord",
                () -> assertEquals("Bertil The Lich Lord", character.getCharacterName()),
                () -> runAbilitiesAssertion(character, Arrays.asList(
                        WeaponAttack.class, HeavyAttack.class, Whirlwind.class,
                        ElementalBolt.class, ElementalBlast.class, FocusedHeal.class), 6)
        );
    }

    @Test
    public void testWarriorCreation() {
        BaseCharacter character = new Warrior(heroName);

        assertAll("Validate creation of Warrior hero",
                () -> assertEquals(heroName + " The Warrior", character.getCharacterName()),
                () -> runAbilitiesAssertion(character, Arrays.asList(
                        WeaponAttack.class, HeavyAttack.class, Whirlwind.class), 3)
        );
    }

    @Test
    public void testRangerCreation() {
        BaseCharacter character = new Ranger(heroName);

        assertAll("Validate creation of Ranger hero",
                () -> assertEquals(heroName + " The Ranger", character.getCharacterName()),
                () -> runAbilitiesAssertion(character, Arrays.asList(
                        WeaponAttack.class, FocusedShot.class, SprayOfArrows.class), 3)
        );
    }

    @Test
    public void testWizardCreation() {
        BaseCharacter character = new Wizard(heroName);

        assertAll("Validate creation of Wizard hero",
                () -> assertEquals(heroName + " The Wizard", character.getCharacterName()),
                () -> runAbilitiesAssertion(character, Arrays.asList(
                        WeaponAttack.class, ElementalBolt.class, ElementalBlast.class), 7)
        );
    }

    @Test
    public void testClericCreation() {
        BaseCharacter character = new Cleric(heroName);

        assertAll("Validate creation of Cleric hero",
                () -> assertEquals(heroName + " The Cleric", character.getCharacterName()),
                () -> runAbilitiesAssertion(character, Arrays.asList(
                        WeaponAttack.class, FocusedHeal.class, GroupHeal.class), 3)
        );
    }
}
