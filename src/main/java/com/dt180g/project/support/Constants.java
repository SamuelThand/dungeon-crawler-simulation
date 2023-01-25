package com.dt180g.project.support;

import java.util.Arrays;
import java.util.List;

/**
 * Utility interface used to define constant values.
 * Much of the game may be configured by modifying these values.
 */
public interface Constants {
    // Names of character types
    String CHARACTER_TYPE_HERO = "HERO";
    String CHARACTER_TYPE_ENEMY = "ENEMY";

    // Names of hero types
    String HERO_CLERIC = "Cleric";
    String HERO_RANGER = "Ranger";
    String HERO_WARRIOR = "Warrior";
    String HERO_WIZARD = "Wizard";

    // Names of enemy types
    String ENEMY_SKELETON_ARCHER = "Skeleton Archer";
    String ENEMY_SKELETON_WARRIOR = "Skeleton Warrior";
    String ENEMY_SKELETON_MAGE = "Skeleton Mage";
    String ENEMY_LICH_LORD = "Bertil The Lich Lord";

    // Names of attribute types
    String ATTRIBUTE_STRENGTH = "Strength";
    String ATTRIBUTE_DEXTERITY = "Dexterity";
    String ATTRIBUTE_INTELLIGENCE = "Intelligence";
    String ATTRIBUTE_WILLPOWER = "Willpower";

    // Names of trait types
    String TRAIT_VITALITY = "Vitality";
    String TRAIT_ENERGY = "Energy";
    String TRAIT_ATTACK_RATE = "Attack Rate";
    String TRAIT_DEFENSE_RATE = "Defense Rate";

    // names of combat stat types
    String COMBAT_STAT_ACTION_POINTS = "Action Points";
    String COMBAT_STAT_PHYSICAL_POWER = "Physical Power";
    String COMBAT_STAT_MAGIC_POWER = "Magical Power";
    String COMBAT_STAT_HEALING_POWER = "Healing Power";

    /* VALUES FOR ATTRIBUTES. Used for respective character type. */
    List<Integer> ATTRIBUTE_VALUES_WARRIOR_HERO = Arrays.asList(8, 6, 2, 4);
    List<Integer> ATTRIBUTE_VALUES_RANGER_HERO = Arrays.asList(6, 8, 4, 2);
    List<Integer> ATTRIBUTE_VALUES_WIZARD_HERO = Arrays.asList(2, 4, 8, 6);
    List<Integer> ATTRIBUTE_VALUES_CLERIC_HERO = Arrays.asList(4, 2, 6, 8);
    List<Integer> ATTRIBUTE_VALUES_SKELETON_WARRIOR = Arrays.asList(4, 3, 2, 1);
    List<Integer> ATTRIBUTE_VALUES_SKELETON_ARCHER = Arrays.asList(3, 4, 2, 1);
    List<Integer> ATTRIBUTE_VALUES_SKELETON_MAGE = Arrays.asList(2, 3, 4, 1);
    List<Integer> ATTRIBUTE_VALUES_LICH_LORD = Arrays.asList(10, 10, 8, 8);

    /* NUMERIC STAT VALUES. Used as base for all characters */
    int TRAIT_VITALITY_BASE_VALUE = 200;  // represents HP
    int TRAIT_ENERGY_BASE_VALUE = 100;
    int TRAIT_ATTACK_RATE_BASE_VALUE = 30;
    int TRAIT_DEFENCE_RATE_BASE_VALUE = 30;
    double COMBAT_STAT_MULTIPLIER = .2;     // used for calculating value of combat stats

    int ATTRIBUTE_BASE_VALUE = 10;  // used for calculating attribute values

    // Names of weapon types
    String WEAPON_AXE = "Axe";
    String WEAPON_BOW = "Bow";
    String WEAPON_CROSSBOW = "Crossbow";
    String WEAPON_ORB = "Orb";
    String WEAPON_SHIELD = "Shield";
    String WEAPON_STAFF = "Staff";
    String WEAPON_SWORD = "Sword";
    String WEAPON_TOME = "Tome";
    String WEAPON_WAND = "Wand";

    // Names of armor types
    String ARMOR_CHEST = "Chest";
    String ARMOR_FEET = "Feet";
    String ARMOR_HANDS = "Hands";
    String ARMOR_HEAD = "Head";
    String ARMOR_LEGS = "Legs";

    // Names of element types
    String ELEMENT_FIRE = "Fire";
    String ELEMENT_ICE = "Ice";
    String ELEMENT_AIR = "Air";

    /* MAGICAL PHRASES. Used for casting spells */
    String MAGICAL_PHRASE_1 = "Abracadabra";
    String MAGICAL_PHRASE_2 = "Hocus Pocus";
    String MAGICAL_PHRASE_3 = "Sim Sala Bim";
    String MAGICAL_PHRASE_4 = "Bibbidi-Bobbidi-Boo";

    /* ABILITY NAMES */
    String ABILITY_WEAPON_ATTACK = "Basic Weapon Attack";
    String ABILITY_HEAVY_ATTACK = "Heavy Attack";
    String ABILITY_WHIRLWIND = "Whirlwind";
    String ABILITY_FOCUSED_SHOT = "Focused Shot";
    String ABILITY_SPRAY_OF_ARROWS = "Spray of Arrows";
    String ABILITY_ELEMENTAL_BOLT = "Bolt";
    String ABILITY_ELEMENTAL_BLAST = "Blast";
    String ABILITY_FOCUSED_HEAL = "Focused Heal";
    String ABILITY_GROUP_HEAL = "Group Heal";

    /* ABILITY AP COSTS */
    int LOWEST_AP_COST = 2;
    int MEDIUM_AP_COST = 4;
    int HIGHEST_AP_COST = 6;

    /* ABILITY ENERGY COST */
    int LOW_ENERGY_COST = 20;
    int HIGH_ENERGY_COST = 40;

    /* ABILITY TARGET AMOUNTS */
    int ABILITY_SINGLE_TARGET = 1;
    int ABILITY_GROUP_TARGET = 3;
    int SINGLE_TARGET_ABILITY_MULTIPLIER = 3;  // multiply bonus damage for single target abilities

    int CRIT_CHANCE = 3;                // limit for crit chance calculations (e.g. 3 will be 30%)
    int HERO_CRIT_MULTIPLIER = 3;       // multiplier for hero critical hits
    int ENEMY_CRIT_MULTIPLIER = 2;      // multiplier for enemy critical hits

    int BOSS_HEALTH_MULTIPLIER = 9;     // multiplier for Lich Lord health

    int DUNGEON_START_LEVEL = 5;    // the game will start at this level
    int ENEMIES_MULTIPLIER = 4;     // each new dungeon level will add this amount of enemies
    int ACTIONS_PER_TURN = 7;       // amount of abilities to queue for each character's turn
    int ROUND_RESET_AP = 3;         // amount of Action Points replenished at start of turn
    int ROUND_RESET_ENERGY = 10;    // amount of Energy replenished at start of turn

    /* THREAD DELAY */
    boolean USE_SLEEP_DELAY = true;     // whether the activity logger should apply a sleep delay
    int SLEEP_DELAY = 400;              // amount of ms to sleep between log prints

    int WEAPON_ATTRIBUTE_VALUE_UPPER_BOUND = 10;    // used as upper bound for randomising weapon stat value
    int ARMOR_STAT_VALUE_UPPER_BOUND = 15;          // used as upper bound for randomising armor stat value

    /* ANSI COLOR CODES. Used to colorize output. */
    String ANSI_RESET = "\u001B[0m";
    String ANSI_BLACK = "\u001B[30m";
    String ANSI_RED = "\u001B[31m";
    String ANSI_GREEN = "\u001B[32m";
    String ANSI_YELLOW = "\u001B[33m";
    String ANSI_BLUE = "\u001B[34m";
    String ANSI_PURPLE = "\u001B[35m";
    String ANSI_CYAN = "\u001B[36m";
    String ANSI_WHITE = "\u001B[37m";
    String ANSI_MAGENTA = "\u001b[35m";
}
