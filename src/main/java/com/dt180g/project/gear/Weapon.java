package com.dt180g.project.gear;

import com.dt180g.project.stats.Attribute;
import com.dt180g.project.stats.BaseStat;
import com.dt180g.project.stats.StatsManager;
import com.dt180g.project.support.Constants;
import com.dt180g.project.support.Randomizer;
import java.util.Map;

/**
 * Concrete class for the gear weapon.
 *
 * @author Samuel Thand
 */
public class Weapon extends BaseGear implements Constants {
    private final int damage;
    private final String wield;
    private final Attribute attribute;

    /**
     * Constructor, initializes members from provided map.
     *
     * @param weaponData Mapped values with information about the weapon piece
     */
    public Weapon(final Map<String, String> weaponData) {
        super(weaponData.get("type"), weaponData.get("name"), weaponData.get("restriction"));
        this.damage = Integer.parseInt(weaponData.get("damage"));
        this.wield = weaponData.get("wield");
        this.attribute = createAttributeWithRandomBoost();
    }

    /**
     * Create a random Attribute with a random boost value.
     *
     * @return A random Attribute with a random boost value.
     */
    private Attribute createAttributeWithRandomBoost() {
        int randomBoostValue = Randomizer.INSTANCE.getRandomValue(1, Constants.WEAPON_ATTRIBUTE_VALUE_UPPER_BOUND);
        String randomAttributeName = StatsManager.INSTANCE.getRandomAttributeName();

        return new Attribute(randomAttributeName, randomBoostValue);
    }

    /**
     * Get the damage for this Weapon.
     *
     * @return protection member
     */
    public final int getDamage() {
        return damage;
    }

    /**
     * Get wield of this Weapon.
     *
     * @return material member
     */
    public final String getWield() {
        return wield;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final BaseStat getStat() {
        return attribute;
    }

    /**
     * Check if this Weapon is two-handed.
     *
     * @return This weapon is two-handed.
     */
    public final boolean isTwoHanded() {
        return wield.contains("Two Handed");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return String.format("%s of %s", super.toString(), attribute.getStatName());
    }
}
