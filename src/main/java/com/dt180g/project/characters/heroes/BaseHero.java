package com.dt180g.project.characters.heroes;

import com.dt180g.project.characters.BaseCharacter;
import com.dt180g.project.characters.CharacterEquipment;
import com.dt180g.project.characters.CharacterStats;
import com.dt180g.project.gear.Armor;
import com.dt180g.project.gear.GearManager;
import com.dt180g.project.gear.Weapon;
import com.dt180g.project.stats.BaseStat;
import com.dt180g.project.support.ActivityLogger;
import com.dt180g.project.support.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Base class for the hero hierarchy, declares common interface for all heroes.
 *
 * @author Samuel Thand
 */
public abstract class BaseHero extends BaseCharacter implements Constants {

    private final String characterName;

    /**
     * Base constructor, initializes members.
     *
     * @param characterName The name of this Hero
     * @param attributeValues The starting attribute values for this Hero
     */
    protected BaseHero(final String characterName, final List<Integer> attributeValues) {
        super(new CharacterStats(attributeValues));
        this.characterName = characterName;

    }

    /**
     * Equip hero with weapons and armor.
     *
     * @param classRestriction The class this equipment is for
     */
    protected void equipHero(final Class<?> classRestriction) {

        CharacterEquipment heroEquipment = getEquipment();

        // Weapons
        while (heroEquipment.amountOfEmptyWeaponSlots() != 0) {

            Weapon randomWeapon;
            if (heroEquipment.amountOfEmptyWeaponSlots() == 1) {
                randomWeapon = GearManager.INSTANCE.getRandomOneHandedWeapon(classRestriction);
            } else {
                randomWeapon = GearManager.INSTANCE.getRandomWeapon(classRestriction);
            }

            boolean weaponWasAdded = heroEquipment.addWeapon(randomWeapon);

            if (weaponWasAdded) {
                BaseStat weaponAttributeBoost = randomWeapon.getStat();
                String weaponAttributeBoostName = weaponAttributeBoost.getStatName();
                int weaponAttributeBoostValue = weaponAttributeBoost.getBaseValue();

                getCharacterStats().adjustStatStaticModifier(weaponAttributeBoostName, weaponAttributeBoostValue);
            }
        }

        // Armor
        ArrayList<String> armorSlots = new ArrayList<>(Arrays.asList(
                ARMOR_CHEST, ARMOR_FEET, ARMOR_HANDS, ARMOR_HEAD, ARMOR_LEGS));

        for (String armorSlot : armorSlots) {

            Armor randomArmorForSlot = GearManager.INSTANCE.getRandomArmorOfType(armorSlot, classRestriction);

            boolean armorWasAdded = heroEquipment.addArmorPiece(armorSlot, randomArmorForSlot);

            if (armorWasAdded) {
                BaseStat armorTraitBoost = randomArmorForSlot.getStat();
                String armorTraitBoostName = armorTraitBoost.getStatName();
                int armorTraitBoostValue = armorTraitBoost.getBaseValue();
                getCharacterStats().adjustStatStaticModifier(armorTraitBoostName, armorTraitBoostValue);
            }
        }
    }

    /**
     * Reset the hero's stats.
     */
    public void resetHeroStats() {
        CharacterStats stats = getCharacterStats();
        stats.resetActionPoints();
        stats.resetHitPoints();
        stats.resetEnergyLevel();
    }

    /**
     * {@inheritDoc}
     */
    public String getCharacterName() {
        return characterName;
    }

    /**
     * {@inheritDoc}
     */
    public void doTurn() {
        String heroTurnInfo = getTurnInformation("[" + CHARACTER_TYPE_HERO + " TURN] " + getCharacterName());
        ActivityLogger.INSTANCE.logTurnInfo(heroTurnInfo);
        super.executeActions(true);
    }
}
