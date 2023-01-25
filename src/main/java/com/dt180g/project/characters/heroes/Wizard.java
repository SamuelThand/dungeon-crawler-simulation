package com.dt180g.project.characters.heroes;

import com.dt180g.project.abilities.ElementalBlast;
import com.dt180g.project.abilities.ElementalBolt;
import com.dt180g.project.abilities.WeaponAttack;
import com.dt180g.project.support.Constants;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Concrete class for the hero Wizard.
 *
 * @author Samuel Thand
 */
public class Wizard extends BaseHero implements Constants {

    /**
     * Constructor, initializes members.
     *
     * @param firstName The first name of this Wizard
     */
    public Wizard(final String firstName) {
        super(firstName + " The " + HERO_WIZARD, ATTRIBUTE_VALUES_WIZARD_HERO);
        super.equipHero(this.getClass());
        super.addAbilities(new ArrayList<>(
                Arrays.asList(new WeaponAttack(), new ElementalBolt(ELEMENT_FIRE),
                        new ElementalBolt(ELEMENT_ICE), new ElementalBolt(Constants.ELEMENT_AIR),
                        new ElementalBlast(ELEMENT_FIRE), new ElementalBlast(ELEMENT_ICE),
                        new ElementalBlast(ELEMENT_AIR))));
    }
}
