package com.dt180g.project.characters.enemies;

import com.dt180g.project.characters.BaseCharacter;
import com.dt180g.project.characters.CharacterEquipment;
import com.dt180g.project.characters.CharacterStats;
import com.dt180g.project.gear.GearManager;
import com.dt180g.project.gear.Weapon;
import com.dt180g.project.stats.BaseStat;
import com.dt180g.project.support.ActivityLogger;
import com.dt180g.project.support.Constants;
import java.util.List;

/**
 * Base class for the enemy hierarchy, declares common interface for all enemies.
 *
 * @author Samuel Thand
 */
public class BaseEnemy extends BaseCharacter implements Constants {

    private final String characterName;

    /**
     * Base constructor, initializes members.
     *
     * @param characterName The name of this Hero
     * @param attributeValues The starting attribute values for this Hero
     */
    protected BaseEnemy(final String characterName, final List<Integer> attributeValues) {
        super(new CharacterStats(attributeValues));
        this.characterName = characterName;
    }

    /**
     * Equip Enemy with weapons and armor.
     *
     * @param equipment List of names of allowed weapon types
     */
    protected void equipEnemy(final List<String> equipment) {

        CharacterEquipment enemyEquipment = getEquipment();

        while (enemyEquipment.amountOfEmptyWeaponSlots() != 0) {

            Weapon randomWeapon;
            if (enemyEquipment.amountOfEmptyWeaponSlots() == 1) {
                randomWeapon = GearManager.INSTANCE.getRandomOneHandedWeapon(equipment);
            } else {
                randomWeapon = GearManager.INSTANCE.getRandomWeapon(equipment);
            }

            boolean weaponWasAdded = enemyEquipment.addWeapon(randomWeapon);

            if (weaponWasAdded) {
                BaseStat weaponAttributeBoost = randomWeapon.getStat();
                String weaponAttributeBoostName = weaponAttributeBoost.getStatName();
                int weaponAttributeBoostValue = weaponAttributeBoost.getBaseValue();

                getCharacterStats().adjustStatStaticModifier(weaponAttributeBoostName, weaponAttributeBoostValue);
            }
        }
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
        String enemyTurnInfo = getTurnInformation("[" + CHARACTER_TYPE_ENEMY + " TURN] " + getCharacterName());
        ActivityLogger.INSTANCE.logTurnInfo(enemyTurnInfo);
        super.executeActions(false);
    }
}
