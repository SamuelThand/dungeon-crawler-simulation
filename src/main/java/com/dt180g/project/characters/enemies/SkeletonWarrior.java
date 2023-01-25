package com.dt180g.project.characters.enemies;

import com.dt180g.project.abilities.HeavyAttack;
import com.dt180g.project.abilities.WeaponAttack;
import com.dt180g.project.abilities.Whirlwind;
import com.dt180g.project.support.Constants;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Concrete class for the enemy SkeletonWarrior.
 *
 * @author Samuel Thand
 */
public class SkeletonWarrior extends BaseEnemy implements Constants {

    /**
     * Constructor, initializes members.
     *
     * @param sequenceNumber The number of this SkeletonWarrior
     */
    public SkeletonWarrior(final int sequenceNumber) {
        super(ENEMY_SKELETON_WARRIOR + " " + sequenceNumber, ATTRIBUTE_VALUES_SKELETON_WARRIOR);
        super.equipEnemy(new ArrayList<>(Arrays.asList(WEAPON_AXE, WEAPON_SWORD, WEAPON_SHIELD)));
        super.addAbilities(new ArrayList<>(Arrays.asList(new WeaponAttack(), new HeavyAttack(), new Whirlwind())));
    }
}
