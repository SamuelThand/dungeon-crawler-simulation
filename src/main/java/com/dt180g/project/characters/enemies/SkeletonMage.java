package com.dt180g.project.characters.enemies;

import com.dt180g.project.abilities.ElementalBlast;
import com.dt180g.project.abilities.ElementalBolt;
import com.dt180g.project.abilities.WeaponAttack;
import com.dt180g.project.support.Constants;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Concrete class for the enemy SkeletonMage.
 *
 * @author Samuel Thand
 */
public class SkeletonMage extends BaseEnemy implements Constants {

    /**
     * Constructor, initializes members.
     *
     * @param sequenceNumber The number of this SkeletonMage
     */
    public SkeletonMage(final int sequenceNumber) {
        super(ENEMY_SKELETON_MAGE + " " + sequenceNumber, ATTRIBUTE_VALUES_SKELETON_MAGE);
        super.equipEnemy(new ArrayList<>(Arrays.asList(WEAPON_STAFF, WEAPON_WAND)));
        super.addAbilities(new ArrayList<>(Arrays.asList(new WeaponAttack(),
                new ElementalBolt(ELEMENT_FIRE), new ElementalBolt(ELEMENT_ICE),
                new ElementalBolt(ELEMENT_AIR), new ElementalBlast(ELEMENT_FIRE),
                new ElementalBlast(ELEMENT_ICE), new ElementalBlast(ELEMENT_AIR))));
    }
}
