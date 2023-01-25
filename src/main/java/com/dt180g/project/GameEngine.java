package com.dt180g.project;

import com.dt180g.project.abilities.AbilityInfo;

import com.dt180g.project.characters.BaseCharacter;
import com.dt180g.project.characters.enemies.*;
import com.dt180g.project.characters.heroes.*;

import com.dt180g.project.support.ActivityLogger;
import com.dt180g.project.support.Constants;
import com.dt180g.project.support.Randomizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Entity responsible for game state and character activities.
 * @author Erik Ström
 */
public final class GameEngine {
    public static final GameEngine INSTANCE = new GameEngine();
    private final List<BaseHero> heroes;
    private final List<BaseEnemy> enemies = new LinkedList<>();
    private final List<String> enemyTypes;

    /**
     * Constructor initialising needed members.
     */
    private GameEngine() {
        heroes = new ArrayList<>(Arrays.asList(
                new Warrior("Leila"), new Ranger("Allan"),
                new Wizard("Elvira"), new Cleric("Kevin")));

        enemyTypes = Arrays.asList(
                Constants.ENEMY_SKELETON_WARRIOR, Constants.ENEMY_SKELETON_ARCHER, Constants.ENEMY_SKELETON_MAGE);
    }

    /**
     * Get index of targets for character ability.
     * @param amountOfTargets the number of targets needed
     * @param listSize size of container holding target characters
     * @return container of integer positions
     */
    private List<Integer> getTargetIndexPos(final int amountOfTargets, final int listSize) {
        List<Integer> targets = new ArrayList<>();

        while (targets.size() < amountOfTargets) {
            int randVal = Randomizer.INSTANCE.getRandomValue(listSize - 1);
            targets.add(randVal);
        }
        return targets;
    }

    /**
     * Accessor to get hero characters.
     * @return list of remaining hero characters.
     */
    public List<BaseHero> getHeroes() { return heroes; }

    /**
     * Accessor to get enemy characters.
     * @return list of remaining enemy characters.
     */
    public List<BaseEnemy> getEnemies() { return enemies; }

    /**
     * Accessor to get amount of available enemy characters.
     * @return amount of remaining enemies.
     */
    public int getAmountOfEnemies() { return enemies.size(); }

    /**
     * Accessor to get amount of available hero characters.
     * @return amount of remaining heroes.
     */
    public int getAmountOfHeroes() { return heroes.size(); }

    /**
     * Used to reset stats of remaining heroes. Represents resting between dungeon levels.
     */
    public void resetHeroesStats() { heroes.forEach(BaseHero::resetHeroStats); }

    /**
     * Accessor to get all available characters, both heroes and enemies.
     * @return list of characters, as combination of remaining heroes and enemies.
     */
    public List<BaseCharacter> getAllCharacters() {
        List<BaseCharacter> allCharacters = new ArrayList<>();
        allCharacters.addAll(heroes);
        allCharacters.addAll(enemies);
        return allCharacters;
    }

    /**
     * Descriptor method, used to determine if there are heroes left.
     * @return whether there are heroes remaining.
     */
    public boolean heroesRemaining() { return !heroes.isEmpty(); }

    /**
     * Descriptor method, used to determine if there are enemies left.
     * @return whether there are enemies remaining.
     */
    public boolean enemiesRemaining() { return !enemies.isEmpty(); }

    /**
     * Fill list of enemies with stated amount.
     * @param amountOfEnemies the amount of basic enemies to produce.
     * @param finalBoss whether final boss should be produced.
     */
    public void produceEnemies(final int amountOfEnemies, final boolean finalBoss) {
        enemies.clear();  // be sure we start with an empty list

        if (finalBoss) {  // only add the final boss
            enemies.add(new LichLord());
            return;
        }

        Map<String, Integer> amountOfTypes = new HashMap<>();
        for (int i = 0; i < amountOfEnemies; i++) {
            int randVal = Randomizer.INSTANCE.getRandomValue(enemyTypes.size() - 1);
            String randStr = enemyTypes.get(randVal);

            int enemyCounter = (amountOfTypes.containsKey(randStr)) ? amountOfTypes.get(randStr) + 1 : 1;
            amountOfTypes.put(randStr, enemyCounter);

            switch (randStr) {
                case Constants.ENEMY_SKELETON_ARCHER: enemies.add(new SkeletonArcher(enemyCounter)); break;
                case Constants.ENEMY_SKELETON_MAGE: enemies.add(new SkeletonMage(enemyCounter)); break;
                default: enemies.add(new SkeletonWarrior(enemyCounter));
            }
        }
    }

    /* ATTACK PROCEDURES */

    /**
     * Used internally by Game Engine to perform character attacks.
     * @param targetList list containing character types which the attack targets.
     * @param abilityInfo information about the ability to base the attack.
     * @param critMultiplier multiplier for attack value (critical hit).
     */
    private void performCharacterAttack(final List<? extends BaseCharacter> targetList, final AbilityInfo abilityInfo,
                                        final int critMultiplier) {
        List<BaseCharacter> toDie = new ArrayList<>();

        for (int charPos : getTargetIndexPos(abilityInfo.getAmountOfTargets(), targetList.size())) {
            BaseCharacter character = targetList.get(charPos);  // current target

            if (toDie.contains(character)) {  // already marked for death? No need to continue then!
                continue;
            }

            final int critUpperBound = 9;
            boolean addCrit = Randomizer.INSTANCE.getRandomValue(1, critUpperBound)
                    <= Constants.CRIT_CHANCE;
            int damage = (addCrit)
                    ? abilityInfo.getDamage() * critMultiplier : abilityInfo.getDamage();

            String baseInfo = String.format("%s%s", (addCrit) ? "[CRITICAL HIT] " : "", character.getCharacterName());

            if (abilityInfo.isHeal()) {  // is the intention to heal?
                String logInfo = String.format("%s receives %+d points of healing",
                        baseInfo, -damage);  // heal by damage amount
                ActivityLogger.INSTANCE.logHealing(String.format("%s and has %d HP left",
                        logInfo, character.registerHealing(-damage)));
                continue;
            }

            /*
                We have a damaging attack. Get damage information as a list of two values.
                The first value is the amount deflected from protective gear, while the
                other is actual damage made. Magic abilities cannot be mitigated!
             */
            List<Integer> damageInfo = character.registerDamage(damage, abilityInfo.isMagic());

            String logInfo = String.format("%s deflects %d points of damage taking %d as direct hit",
                    baseInfo, damageInfo.get(0), damageInfo.get(1));


            if (character.getHitPoints() < 1) {  // 0 or less HP means death!
                ActivityLogger.INSTANCE.logDeath(String.format("%s causing death (%d HP)",
                        logInfo, character.getHitPoints()));
                toDie.add(character);  // schedule for removal
                continue;
            }

            ActivityLogger.INSTANCE.logDamage(String.format("%s and has %d HP left",
                    logInfo, character.getHitPoints()));
        }

        /*
            Clean up dead characters from target list. Note that we could have placed each target
            to be removed in a Set and simply used method removeAll, instead of full iteration.
            However, in order for it to be efficient character classes would need to override equals().
         */
        ListIterator<? extends BaseCharacter> iter = targetList.listIterator();
        while (iter.hasNext()) {
            if (iter.next().isDead()) {
                iter.remove();
            }
        }
    }

    /**
     * Used internally by Game Engine for hero specific attack.
     * @param abilityInfo information about the ability to base the attack.
     * @return whether the attack has been performed successfully.
     */
    private boolean heroAttack(final AbilityInfo abilityInfo) {
        if (enemies.isEmpty()) {
            ActivityLogger.INSTANCE.logAttack("All enemies are dead...");
            return false;
        }

        int amountOfTargets = abilityInfo.getAmountOfTargets();
        String targets = (amountOfTargets == 1) ? "enemy" : "enemies";
        ActivityLogger.INSTANCE.logAttack(String.format("%s targeting %d %s",
                abilityInfo.getInformation(), amountOfTargets, targets));
        performCharacterAttack(enemies, abilityInfo, Constants.HERO_CRIT_MULTIPLIER);
        return true;
    }

    /**
     * Used internally by Game Engine for enemy specific attack.
     * @param abilityInfo information about the ability to base the attack.
     * @return whether the attack has been performed successfully.
     */
    private boolean enemyAttack(final AbilityInfo abilityInfo) {
        if (heroes.isEmpty()) {
            ActivityLogger.INSTANCE.logAttack("All heroes are dead...");
            return false;
        }

        int amountOfTargets = abilityInfo.getAmountOfTargets();
        String targets = (amountOfTargets == 1) ? "hero" : "heroes";
        ActivityLogger.INSTANCE.logAttack(String.format("%s targeting %d %s",
                abilityInfo.getInformation(), amountOfTargets, targets));
        performCharacterAttack(heroes, abilityInfo, Constants.ENEMY_CRIT_MULTIPLIER);
        return true;
    }

    /**
     * Used by client to request that the engine performs an attack.
     * @param abilityInfo information about the ability to base the attack.
     * @return whether the attack has been performed successfully.
     */
    public boolean characterAttack(final AbilityInfo abilityInfo) {
        if (abilityInfo.getTargetEnemies()) {
            return heroAttack(abilityInfo);
        }
        return enemyAttack(abilityInfo);
    }
}
