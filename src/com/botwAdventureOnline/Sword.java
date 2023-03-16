package com.botwAdventureOnline;

public class Sword extends Item {
    /**
     * @param pName   The name of the sword
     * @param pWeight The weight of the sword
     * @param pDamage The sword's damage
     */
    public Sword(String pName, int pWeight, int pDamage) {
        super(pName, pWeight, (pDamage - 100) * 3, 0, 0, pDamage);
    }
}
