package com.dt180g.project.characters.heroes;

import com.dt180g.project.abilities.FocusedShot;
import com.dt180g.project.abilities.SprayOfArrows;
import com.dt180g.project.abilities.WeaponAttack;
import com.dt180g.project.support.Constants;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Concrete class for the hero Ranger.
 *
 * @author Samuel Thand
 */
public class Ranger extends BaseHero implements Constants {

    /**
     * Constructor, initializes members.
     *
     * @param firstName The first name of this Ranger
     */
    public Ranger(final String firstName) {
        super(firstName + " The " + HERO_RANGER, ATTRIBUTE_VALUES_RANGER_HERO);
        super.equipHero(this.getClass());
        super.addAbilities(new ArrayList<>(Arrays.asList(new WeaponAttack(), new FocusedShot(), new SprayOfArrows())));
    }
}
