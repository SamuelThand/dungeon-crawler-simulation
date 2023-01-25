package com.dt180g.project.characters.enemies;

import com.dt180g.project.abilities.FocusedShot;
import com.dt180g.project.abilities.SprayOfArrows;
import com.dt180g.project.abilities.WeaponAttack;
import com.dt180g.project.support.Constants;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Concrete class for the enemy SkeletonArcher.
 *
 * @author Samuel Thand
 */
public class SkeletonArcher extends BaseEnemy implements Constants {

    /**
     * Constructor, initializes members.
     *
     * @param sequenceNumber The number of this SkeletonArcher
     */
    public SkeletonArcher(final int sequenceNumber) {
        super(ENEMY_SKELETON_ARCHER + " " + sequenceNumber, ATTRIBUTE_VALUES_SKELETON_ARCHER);
        super.equipEnemy(new ArrayList<>(Arrays.asList(WEAPON_BOW, WEAPON_CROSSBOW)));
        super.addAbilities(new ArrayList<>(Arrays.asList(new WeaponAttack(), new FocusedShot(), new SprayOfArrows())));
    }
}
