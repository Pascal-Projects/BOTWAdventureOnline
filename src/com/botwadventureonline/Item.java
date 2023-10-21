package com.botwadventureonline;

import java.io.Serializable;

public class Item implements Serializable {
    /**
     * The name of the item
     */
    private final String name;
    /**
     * The weight of the item
     */
    private final int weight;
    /**
     * The value of the item in rupees
     */
    private final int value;
    /**
     * The amount of experience points if the item is an Experience Potion
     */
    private final int experiencePoints;
    /**
     * The amount of health points if the item is a Health Potion
     */
    private final int healthPoints;
    /**
     * The amount of damage if the item is a Sword
     */
    private final int damage;

    /**
     * @param pName             Name of the item
     * @param pWeight           Weight of the item
     * @param pValue            Value of the item in rupees
     * @param pExperiencePoints Experience points if the item is an Experience Potion
     * @param pHealthPoints     Health points if the item is a Health Potion
     * @param pDamage           Damage if the item is a Sword
     */
    public Item(String pName, int pWeight, int pValue, int pExperiencePoints, int pHealthPoints, int pDamage) {
        name = pName;
        weight = pWeight;
        value = pValue;
        experiencePoints = pExperiencePoints;
        healthPoints = pHealthPoints;
        damage = pDamage;
    }

    /**
     * @return Returns the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * @return Returns the weight of the item
     */
    public int getWeightInt() {
        return weight;
    }

    /**
     * @return Returns the value of the item in rupees
     */
    public String getValue() {
        return Integer.toString(value);
    }

    /**
     * @return Returns the value of the item in rupees as Integer
     */
    public int getValueInt() {
        return value;
    }


    /**
     * @return Returns the amount of experience points if the item is an Experience Potion
     */
    public String getExperiencePoints() {
        return Integer.toString(experiencePoints);
    }

    /**
     * @return Returns the amount of experience points if the item is an Experience Potion as Integer
     */
    public int getExperiencePointsInt() {
        return experiencePoints;
    }

    /**
     * @return Returns the amount of health points if the item is a Health Potion
     */
    public String getHealthPoints() {
        return Integer.toString(healthPoints);
    }

    /**
     * @return Returns the amount of health points if the item is a Health Potion as Integer
     */
    public int getHealthPointsInt() {
        return healthPoints;
    }

    /**
     * @return Returns the amount of damage if the item is a Sword
     */
    public String getDamage() {
        return Integer.toString(damage);
    }

    /**
     * @return Returns the amount of damage if the item is a Sword as Integer
     */
    public int getDamageInt() {
        return damage;
    }
}
