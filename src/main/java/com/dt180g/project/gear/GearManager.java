package com.dt180g.project.gear;

import com.dt180g.project.support.IOHelper;
import com.dt180g.project.support.Randomizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The singleton class responsible for managing gear by loading it from
 * XML, storing it and providing a public interface for accessing these.
 *
 * @author Samuel Thand
 */
public final class GearManager {
    public static final GearManager INSTANCE = new GearManager();
    private final Map<String, List<Weapon>> weapons = new HashMap<>();
    private final Map<String, List<Armor>> armorPieces = new HashMap<>();

    /**
     * Constructor, initializes members.
     */
    private GearManager() {
        loadWeaponsToMemberFromXML(weapons);
        loadArmorToMemberFromXML(armorPieces);
    }

    /**
     * Load weapons from XML to a member.
     * Iterates over the info for each weapon, creates a key-value pair
     * for each weapon type, and adds all weapons of that type to the list
     * as Weapon objects.
     *
     * @param member member to load weapons into
     */
    private void loadWeaponsToMemberFromXML(final Map<String, List<Weapon>> member) {
        List<Map<String, String>> weaponsInfoFromFile = IOHelper.readFromFile("weapons.xml");

        weaponsInfoFromFile.forEach(weaponInfo -> {
            String thisWeaponType = weaponInfo.get("type");
            if (!member.containsKey(thisWeaponType)) {
                member.put(thisWeaponType, new ArrayList<>() {
                    {
                        add(new Weapon(weaponInfo));
                    }
                });
            } else {
                member.get(thisWeaponType).add(new Weapon(weaponInfo));
            }
        });
    }

    /**
     * Load armor from XML to a member.
     * Iterates over the info for each armor piece, creates a key-value pair
     * for each armor type, and adds all armor pieces of that type to the list
     * as Armor objects.
     *
     * @param member member to load armor into
     */
    private void loadArmorToMemberFromXML(final Map<String, List<Armor>> member) {
        List<Map<String, String>> armorInfoFromFile = IOHelper.readFromFile("armor.xml");

        armorInfoFromFile.forEach(armorInfo -> {
            String thisArmorType = armorInfo.get("type");
            if (!member.containsKey(thisArmorType)) {
                member.put(thisArmorType, new ArrayList<>() {
                    {
                        add(new Armor(armorInfo));
                    }
                });
            } else {
                member.get(thisArmorType).add(new Armor(armorInfo));
            }
        });
    }

    /**
     * Select a random index in a list.
     *
     * @param list to index into
     * @return random index from the list
     */
    private int getRandomIndex(final List<?> list) {
        int randomIndex = 0;
        if (list.size() != 0) {
            randomIndex = Randomizer.INSTANCE.getRandomValue(list.size() - 1);
        }

        return randomIndex;
    }

    /**
     * Get a list of all weapons available for the specified class.
     * Iterates over all weapon types and all weapons of that type, and checks if the
     * specified class can use it.
     *
     * @param classRestriction class to find weapons for
     * @return A list of all available weapons for the class
     */
    private List<Weapon> getAllWeaponsForRestriction(final Class<?> classRestriction) {
        ArrayList<Weapon> allowedWeaponsForClass = new ArrayList<>();
        getAllMappedWeapons().forEach((weaponType, weaponsInList) -> weaponsInList.forEach(weapon -> {
            if (weapon.checkClassRestriction(classRestriction)) {
                allowedWeaponsForClass.add(weapon);
            }
        }));

        return allowedWeaponsForClass;
    }

    /**
     * Get a list of one-handed weapons from a list of weapons.
     * Iterates over all weapons in the list and checks if they are one-handed.
     *
     * @param weaponsInList list to parse for one-handed weapons.
     * @return A list of one-handed weapons
     */
    private List<Weapon> getOneHandedWeapons(final List<Weapon> weaponsInList) {
        List<Weapon> oneHandedWeapons = new ArrayList<>();
        weaponsInList.forEach(weapon -> {
            if (!weapon.isTwoHanded()) {
                oneHandedWeapons.add(weapon);
            }
        });

        return oneHandedWeapons;
    }

    /**
     * Get all Armor from stored armorPieces.
     *
     * @return armorPieces member
     */
    public Map<String, List<Armor>> getAllMappedArmorPieces() {
        return armorPieces;
    }

    /**
     * Get all weapons from stored weapons.
     *
     * @return weapons member
     */
    public Map<String, List<Weapon>> getAllMappedWeapons() {
        return weapons;
    }

    /**
     * Get all weapons of a specified type from stored weapons.
     *
     * @param type Type of weapon
     * @return List of weapons of type
     */
    public List<Weapon> getWeaponsOfType(final String type) {
        return weapons.get(type);
    }

    /**
     * Get a random Weapon available to the specified class.
     *
     * @param classRestriction Class to get a random Weapon for
     * @return A random Weapon for the class
     */
    public Weapon getRandomWeapon(final Class<?> classRestriction) {
        List<Weapon> allowedWeaponsForClass = getAllWeaponsForRestriction(classRestriction);
        int randomIndex = getRandomIndex(allowedWeaponsForClass);

        return allowedWeaponsForClass.get(randomIndex);
    }

    /**
     * Get a random Weapon of a random type from the specified list.
     *
     * @param weaponTypes List of Weapon types
     * @return A random Weapon of a random type
     */
    public Weapon getRandomWeapon(final List<String> weaponTypes) {
        String randomWeaponType = weaponTypes.get(getRandomIndex(weaponTypes));
        List<Weapon> weaponsOfRandomType = weapons.get(randomWeaponType);
        int randomIndex = getRandomIndex(weaponsOfRandomType);

        return weaponsOfRandomType.get(randomIndex);
    }

    /**
     * Get a random one-handed Weapon available to the specified class.
     *
     * @param classRestriction Class to get a random one-handed Weapon for
     * @return A random one-handed Weapon for the class
     */
    public Weapon getRandomOneHandedWeapon(final Class<?> classRestriction) {
        List<Weapon> allowed1hWeaponsForClass = getOneHandedWeapons(getAllWeaponsForRestriction(classRestriction));
        int randomIndex = getRandomIndex(allowed1hWeaponsForClass);

        return allowed1hWeaponsForClass.get(randomIndex);
    }

    /**
     * Get a random one-handed Weapon of a random type from the specified list.
     *
     * @param weaponTypes List of Weapon types
     * @return A random one-handed Weapon of a random type
     */
    public Weapon getRandomOneHandedWeapon(final List<String> weaponTypes) {
        List<Weapon> oneHandedWeapons = new ArrayList<>();

        for (String weaponType : weaponTypes) {
            List<Weapon> oneHandedWeaponsOfThisType = getOneHandedWeapons(getWeaponsOfType(weaponType));
            oneHandedWeapons.addAll(oneHandedWeaponsOfThisType);
        }

        int randomIndex = getRandomIndex(oneHandedWeapons);

        return oneHandedWeapons.get(randomIndex);
    }

    /**
     * Get a list of all Armor available for the specified class.
     * Iterates over all Armor types and all Armor pieces of that type, and checks if the
     * specified class can use it.
     *
     * @param classRestriction class to find Armor for
     * @return A list of all available Armor for the class
     */
    public List<Armor> getAllArmorForRestriction(final Class<?> classRestriction) {
        ArrayList<Armor> allowedArmorForClass = new ArrayList<>();
        getAllMappedArmorPieces().forEach((armorType, armors) -> armors.forEach(armor -> {
            if (armor.checkClassRestriction(classRestriction)) {
                allowedArmorForClass.add(armor);
            }
        }));

        return allowedArmorForClass;
    }

    /**
     * Get a random Armor piece of the specified type, for the specified class.
     * Iterates over all available Armor for the class and selects pieces of the correct type.
     *
     * @param armorType type of Armor
     * @param classRestriction class to find Armor for
     * @return A list of all available Armor for the class
     */
    public Armor getRandomArmorOfType(final String armorType, final Class<?> classRestriction) {
        List<Armor> allowedArmorForClass = getAllArmorForRestriction(classRestriction);

        List<Armor> allowedArmorsOfType = new ArrayList<>();
        allowedArmorForClass.forEach(armor -> {
            if (armor.getType().equals(armorType)) {
                allowedArmorsOfType.add(armor);
            }
        });

        int randomIndex = getRandomIndex(allowedArmorsOfType);

        return allowedArmorsOfType.get(randomIndex);
    }
}
