package com.dt180g.project.characters.heroes;

import com.dt180g.project.abilities.HeavyAttack;
import com.dt180g.project.abilities.WeaponAttack;
import com.dt180g.project.abilities.Whirlwind;
import com.dt180g.project.support.Constants;
import java.util.ArrayList;
import java.util.Arrays;

public class Warrior extends BaseHero implements Constants {

    /**
     * Constructor, initializes members.
     *
     * @param firstName The first name of this Warrior
     */
    public Warrior(final String firstName) {
        super(firstName + " The " + HERO_WARRIOR, ATTRIBUTE_VALUES_WARRIOR_HERO);
        super.equipHero(this.getClass());
        super.addAbilities(new ArrayList<>(Arrays.asList(new WeaponAttack(), new HeavyAttack(), new Whirlwind())));
    }
}
