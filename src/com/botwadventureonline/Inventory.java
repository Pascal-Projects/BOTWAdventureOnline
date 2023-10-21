package com.botwadventureonline;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements Serializable {

    /**
     * The currently equipped sword of the player
     */
    private Item equippedSword;
    /**
     * The player's potions
     */
    private final List<Item> potions;
    /**
     * The player's swords
     */
    private final List<Item> swords;
    /**
     * The player's rupees
     */
    private int rupees;
    /**
     * The player's korokseeds
     */
    private int korokSeeds;
    /**
     * The inventory's maximum weight
     */
    private int maxWeight;
    /**
     * The inventory's current weight
     */
    private int weight;

    /**
     * @param pMaxWeight The maximum weight of the player at the start of the game
     */
    public Inventory(int pMaxWeight) {
        maxWeight = pMaxWeight;
        potions = new ArrayList<>();
        swords = new ArrayList<>();
        rupees = 50;
        korokSeeds = 0;
        weight = 0;
    }


    /**
     * @return Returns the Potions
     */
    public List<Item> getPotions() {
        return potions;
    }

    /**
     * @return Returns the equipped sword
     */
    public Item getEquippedSword() {
        return equippedSword;
    }

    /**
     * Sets the equipped sword of the player
     *
     * @param equippedSword The new equipped sword
     */
    public void setEquippedSword(Item equippedSword) {
        this.equippedSword = equippedSword;
    }

    /**
     * @param potion The Potion that gets added to the player's potions
     */
    public void addPotion(Item potion) {
        potions.add(potion);
    }

    /**
     * @return returns the swords
     */
    public List<Item> getSwords() {
        return swords;
    }

    /**
     * @param sword The sword that gets added to the player's swords
     */
    public void addSword(Item sword) {
        swords.add(sword);
    }

    /**
     * @return Returns the maximum weight of the inventory
     */
    public int getMaxWeight() {
        return maxWeight;
    }

    /**
     * @return Returns the current weight of the inventory
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @return Returns the rupees of the player
     */
    public int getRupees() {
        return rupees;
    }

    /**
     * @param pRupees The new amount of rupees
     */
    public void setRupees(int pRupees) {
        rupees = pRupees;
    }

    /**
     * @param korokSeeds The new amount of korokseeds
     */
    public void setKorokSeeds(int korokSeeds) {
        this.korokSeeds = korokSeeds;
    }

    /**
     * @return Returns the percentage of the weight's usage
     */
    public double getUsage() {
        return (100.0 / maxWeight) * weight;
    }

    /**
     * @return Returns the amount of korokseeds the player has
     */
    public int getKorokSeeds() {
        return korokSeeds;
    }


    /**
     * @param maxWeight The new maximum weight of the inventory
     */
    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }


    /**
     * @param weight The new current weight of the inventory
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * @param item Potion that gets removed from the player's potions
     */
    public void removePotion(Item item) {
        potions.remove(item);
    }

    /**
     * @param item Sword that gets removed from the player's swords
     */
    public void removeSword(Item item) {
        swords.remove(item);
    }

    /**
     * @return Return the amount of potions and swords in total
     */
    public int getItemCount() {
        return potions.size() + swords.size();
    }
}
