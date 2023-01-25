package com.dt180g.project.characters.enemies;

import com.dt180g.project.abilities.ElementalBlast;
import com.dt180g.project.abilities.ElementalBolt;
import com.dt180g.project.abilities.FocusedHeal;
import com.dt180g.project.abilities.HeavyAttack;
import com.dt180g.project.abilities.WeaponAttack;
import com.dt180g.project.abilities.Whirlwind;
import com.dt180g.project.characters.CharacterStats;
import com.dt180g.project.stats.BaseStat;
import com.dt180g.project.support.Constants;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Concrete class for the enemy LichLord.
 *
 * @author Samuel Thand
 */
public class LichLord extends BaseEnemy implements Constants {

    /**
     * Constructor, initializes members and adds an HP boost.
     */
    public LichLord() {
        super(ENEMY_LICH_LORD, ATTRIBUTE_VALUES_LICH_LORD);
        super.equipEnemy(new ArrayList<>(Arrays.asList(WEAPON_AXE, WEAPON_SWORD, WEAPON_SHIELD)));
        super.addAbilities(new ArrayList<>(Arrays.asList(
                new WeaponAttack(), new HeavyAttack(), new Whirlwind(),
                new FocusedHeal(), new ElementalBolt(ELEMENT_FIRE),
                new ElementalBlast(ELEMENT_FIRE))));

        CharacterStats stats = getCharacterStats();
        BaseStat bossHP = stats.getStat(TRAIT_VITALITY);
        int currentHP = stats.getStatValue(TRAIT_VITALITY);

        bossHP.adjustStaticModifier(currentHP * BOSS_HEALTH_MULTIPLIER);
    }
}
