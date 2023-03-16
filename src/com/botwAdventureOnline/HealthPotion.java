package com.botwAdventureOnline;

/**
 * A Health Potion is a potion that can be used to regain health
 *
 * @extends Potion
 */
public class HealthPotion extends Potion {
    /**
     * @param pHealthPoints Sets the health points of the potion which the
     *                      player gets after drinking it
     */
    public HealthPotion(int pHealthPoints) {
        super("Health Potion", pHealthPoints * 4, pHealthPoints, 0);
    }
}
