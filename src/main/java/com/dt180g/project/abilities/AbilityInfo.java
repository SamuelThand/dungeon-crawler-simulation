package com.dt180g.project.abilities;

public final class AbilityInfo {
    private final String information;
    private final int amountOfTargets;
    private final int damage;
    private final boolean targetEnemies;
    private final boolean isMagic;
    private final boolean isHeal;

    /**
     * Support class to contain ability information to be passed to game engine.
     * @param info log information.
     * @param amountOfTargets number of targets for the ability.
     * @param damage amount of attack damage.
     * @param targetEnemies whether enemies or heroes should be targeted.
     * @param isMagic whether the ability is magical or physical.
     * @param isHeal whether the ability is healing or damaging.
     */
    public AbilityInfo(final String info, final int amountOfTargets, final int damage,
                       final boolean targetEnemies, final boolean isMagic, final boolean isHeal) {
        this.information = info;
        this.amountOfTargets = amountOfTargets;
        this.damage = damage;
        this.targetEnemies = targetEnemies;
        this.isMagic = isMagic;
        this.isHeal = isHeal;
    }

    public String getInformation() { return information; }
    public int getAmountOfTargets() { return amountOfTargets; }
    public int getDamage() { return damage; }
    public boolean getTargetEnemies() { return targetEnemies; }
    public boolean isMagic() { return isMagic; }
    public boolean isHeal() { return isHeal; }

}
