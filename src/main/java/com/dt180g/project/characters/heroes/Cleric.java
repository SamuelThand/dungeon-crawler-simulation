package com.dt180g.project.characters.heroes;

import com.dt180g.project.abilities.FocusedHeal;
import com.dt180g.project.abilities.GroupHeal;
import com.dt180g.project.abilities.WeaponAttack;
import com.dt180g.project.support.Constants;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Concrete class for the hero Cleric.
 *
 * @author Samuel Thand
 */
public class Cleric extends BaseHero implements Constants {

    /**
     * Constructor, initializes members.
     *
     * @param firstName The first name of this Cleric
     */
    public Cleric(final String firstName) {
        super(firstName + " The " + HERO_CLERIC, ATTRIBUTE_VALUES_CLERIC_HERO);
        super.equipHero(this.getClass());
        super.addAbilities(new ArrayList<>(Arrays.asList(new WeaponAttack(), new FocusedHeal(), new GroupHeal())));
    }
}
