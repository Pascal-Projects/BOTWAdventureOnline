package com.botwadventureonline;

public class Monster extends Character {
    private final Item loot;

    /**
     * The Experience points which the Player gets after defeating the monster.
     */
    private final int ep;

    public Monster(String pName, int pHp, int pAd, Item pLoot, int pEp) {
        super(pHp, pAd, pName);
        loot = pLoot;
        ep = pEp;
    }

    /**
     * @return Returns the experience points which the Player gets after defeating the monster.
     */
    public int getEp() {
        return ep;
    }


    /**
     * @return Returns the loot of the monster
     */
    public Item getLoot() {
        return loot;
    }
}
