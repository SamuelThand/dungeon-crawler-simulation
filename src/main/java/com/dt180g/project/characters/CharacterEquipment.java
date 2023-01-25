package com.dt180g.project.characters;

import com.dt180g.project.gear.Armor;
import com.dt180g.project.gear.Weapon;
import com.dt180g.project.support.Constants;
import com.dt180g.project.support.IOHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for managing a characters gear by storing and
 * providing a public interface for accessing these.
 *
 * @author Samuel Thand
 */
public class CharacterEquipment implements Constants {

    private final List<Weapon> weapons = new ArrayList<>();
    private final Map<String, Armor> armorPieces = new HashMap<>();
    private final int maxArmorSlots = 5;
    private final int maxWeaponSlots = 2;

    /**
     * Get the weapons for this BaseCharacter.
     *
     * @return the weapons for this BaseCharacter
     */
    public final List<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * Get the armor pieces for this BaseCharacter.
     *
     * @return the armor pieces for this BaseCharacter
     */
    public final List<Armor> getArmorPieces() {
        return new ArrayList<>(armorPieces.values());
    }

    /**
     * Get the total weapon damage for this BaseCharacter.
     *
     * @return the total weapon damage for this BaseCharacter
     */
    public final int getTotalWeaponDamage() {
        int totalWeaponDamage = 0;
        for (Weapon weapon : weapons) {
            totalWeaponDamage += weapon.getDamage();
        }

        return totalWeaponDamage;
    }

    /**
     * Get the total armor protection for this BaseCharacter.
     *
     * @return the total armor protection for this BaseCharacter
     */
    public final int getTotalArmorProtection() {
        int totalArmorProtection = 0;
        for (Armor armor : armorPieces.values()) {
            totalArmorProtection += armor.getProtection();
        }

        return totalArmorProtection;
    }

    /**
     * Get the base amount of weapon slots for this BaseCharacter.
     *
     * @return the base amount of weapon slots for this BaseCharacter
     */
    private int getMaxWeaponSlots() {
        return maxWeaponSlots;
    }

    /**
     * Get the base amount of armor slots for this BaseCharacter.
     *
     * @return the base amount of weapon slots for this BaseCharacter
     */
    private int getMaxArmorSlots() {
        return maxArmorSlots;
    }

    /**
     * Get the amount of empty weapon slots for this BaseCharacter.
     *
     * @return the amount of empty weapon slots for this BaseCharacter
     */
    public final int amountOfEmptyWeaponSlots() {
        int emptySlots = getMaxWeaponSlots();
        for (Weapon weapon : getWeapons()) {
            if (weapon.isTwoHanded()) {
                emptySlots -= 2;
            } else {
                emptySlots -= 1;
            }
        }

        return emptySlots;
    }

    /**
     * Get the amount of empty armor slots for this BaseCharacter.
     *
     * @return the amount of empty armor slots for this BaseCharacter
     */
    public final int amountOfEmptyArmorSlots() {
        int emptySlots = getMaxArmorSlots();
        for (Armor ignored : armorPieces.values()) {
            emptySlots -= 1;
        }

        return emptySlots;
    }

    /**
     * Add a weapon to this BaseCharacter.
     *
     * @param newWeapon Weapon to add
     * @return The weapon was successfully added
     */
    public final boolean addWeapon(final Weapon newWeapon) {

        int slotRequirement;
        if (newWeapon.isTwoHanded()) {
            slotRequirement = 2;
        } else {
            slotRequirement = 1;
        }

        boolean weaponCanBeAdded = amountOfEmptyWeaponSlots() >= slotRequirement;

        if (weaponCanBeAdded) {
            getWeapons().add(newWeapon);
        }

        return weaponCanBeAdded;
    }

    /**
     * Add an armor piece to this BaseCharacter.
     *
     * @param slot The slot to add armor to
     * @param newArmor The armor to add
     * @return The armor piece was successfully added
     */
    public final boolean addArmorPiece(final String slot, final Armor newArmor) {

        // Checks if the specific slot is empty and number of equipped armor is not maxed
        boolean armorCanBeAdded = !armorPieces.containsKey(slot)
                && amountOfEmptyArmorSlots() != 0;

        if (armorCanBeAdded) {
            armorPieces.put(slot, newArmor);
        }

        return armorCanBeAdded;
    }

    /**
     * Get all information about this BaseCharacters equipment formatted as a table.
     * Takes weapon information, stores it as a list of lists of strings, each
     * list of strings constituting a row with the same amount of strings. This list
     * is passed to IOhelper.formatAsTable() which formats the information.
     *
     * @return Equipment information in a string formatted as a table
     */
    @Override
    public final String toString() {
        List<List<String>> equipmentInfoRows = new ArrayList<>();

        List<String> equipmentHeader = new ArrayList<>();
        equipmentHeader.add(ANSI_BLUE + "EQUIPMENT");

        final int STRINGS_IN_EACH_ROW = 9;
        for (int i = 1; i < STRINGS_IN_EACH_ROW; i++) {
            equipmentHeader.add("");
        }

        equipmentInfoRows.add(equipmentHeader);

        // Build rows with weapon information
        for (Weapon weaponInSlot : weapons) {
            List<String> weaponInfo = new ArrayList<>();

            weaponInfo.add((ANSI_RESET + "[" + weaponInSlot.getType().toUpperCase() + "]"));
            weaponInfo.add((ANSI_WHITE + "|"));

            weaponInfo.add((ANSI_PURPLE + weaponInSlot.getWield()));
            weaponInfo.add((ANSI_WHITE + "|"));

            weaponInfo.add((ANSI_RED + "Damage"));
            weaponInfo.add(String.format("%s%3s", ANSI_GREEN, "+" + weaponInSlot.getDamage()));
            weaponInfo.add((ANSI_WHITE + "|"));

            weaponInfo.add((ANSI_CYAN + weaponInSlot));
            weaponInfo.add(String.format("%s%3s", ANSI_YELLOW, "+" + weaponInSlot.getStat().getModifiedValue()));

            equipmentInfoRows.add(weaponInfo);
        }

        // Build rows with armor information
        for (Armor armorPieceInSlot : getArmorPieces()) {
            List<String> armorInfo = new ArrayList<>();

            armorInfo.add(ANSI_RESET + "[" + armorPieceInSlot.getType().toUpperCase() + "]");
            armorInfo.add((ANSI_WHITE + "|"));

            armorInfo.add((ANSI_PURPLE + armorPieceInSlot.getMaterial()));
            armorInfo.add((ANSI_WHITE + "|"));

            armorInfo.add((ANSI_RED + "Protection"));
            armorInfo.add(String.format("%s%3s", ANSI_GREEN, "+" + armorPieceInSlot.getProtection()));
            armorInfo.add((ANSI_WHITE + "|"));

            armorInfo.add((ANSI_CYAN + armorPieceInSlot));
            armorInfo.add(String.format("%s%3s", ANSI_YELLOW, "+" + armorPieceInSlot.getStat().getModifiedValue()));

            equipmentInfoRows.add(armorInfo);
        }

        return IOHelper.formatAsTable(equipmentInfoRows);
    }
}
