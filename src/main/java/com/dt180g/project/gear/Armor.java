package com.dt180g.project.gear;


import com.dt180g.project.stats.BaseStat;
import com.dt180g.project.stats.StatsManager;
import com.dt180g.project.stats.Trait;
import com.dt180g.project.support.Constants;
import com.dt180g.project.support.Randomizer;
import java.util.Map;

/**
 * Concrete class for the gear armor.
 *
 * @author Samuel Thand
 */
public class Armor extends BaseGear implements Constants {
    private final int protection;
    private final String material;
    private final Trait trait;

    /**
     * Constructor, initializes members from provided map.
     *
     * @param armorData Mapped values with information about the armor piece
     */
    public Armor(final Map<String, String> armorData) {
        super(armorData.get("type"), armorData.get("name"), armorData.get("restriction"));
        this.protection = Integer.parseInt(armorData.get("protection"));
        this.material = armorData.get("material");
        this.trait = createTraitWithRandomBoost();

    }

    /**
     * Create a random Trait with a random boost value.
     *
     * @return A random Trait with a random boost value.
     */
    private Trait createTraitWithRandomBoost() {
        int randomBoostValue = Randomizer.INSTANCE.getRandomValue(1, Constants.ARMOR_STAT_VALUE_UPPER_BOUND);
        String randomTraitName = StatsManager.INSTANCE.getRandomTraitName();

        return new Trait(randomTraitName, randomBoostValue);
    }

    /**
     * Get the protection for this Armor.
     *
     * @return protection member
     */
    public final int getProtection() {
        return protection;
    }

    /**
     * Get the material of this Armor.
     *
     * @return material member
     */
    public final String getMaterial() {
        return material;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final BaseStat getStat() {
        return trait;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return String.format("%s of %s", super.toString(), trait.getStatName());
    }
}
