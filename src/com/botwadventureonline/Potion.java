package com.botwadventureonline;

public class Potion extends Item {
    /**
     * @param pName             Name of the item
     * @param pValue            Value of the item in rupees
     * @param pExperiencePoints Experience points if the item is an Experience Potion
     * @param pHealthPoints     Health points if the item is a Health Potion
     */
    public Potion(String pName, int pValue, int pHealthPoints, int pExperiencePoints) {
        super(pName, 1, pValue, pExperiencePoints, pHealthPoints, 0);
    }
}
