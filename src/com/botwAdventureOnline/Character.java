package com.botwAdventureOnline;

import java.io.Serializable;

/**
 * Class which represents a Character in the game. Both players and monsters are
 * characters
 *
 * @implements Serializable
 */
public class Character implements Serializable {

    /**
     * Health Points of the Character
     */
    private int hp;

    /**
     * Attack damage outgoing from the Character
     */
    private int ad;

    /**
     * Name of the Character
     */
    private final String name;

    /**
     * @param pHp   Set the health points of the Character
     * @param pAd   Set the Characters attack damage
     * @param pName Set the name of the sCharacter
     */
    public Character(int pHp, int pAd, String pName) {
        name = pName;
        hp = pHp;
        ad = pAd;
    }

    /**
     * Sets the health points of the Character
     *
     * @param hp The new health points of the Character
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * @return Returns the current health points of the Character
     */
    public int getHp() {
        return hp;
    }

    /**
     * @return Returns the Character's attack damage
     */
    public int getAd() {
        return ad;
    }

    /**
     * Sets the Character's Attack Damage
     *
     * @param pAd The Value of the new attack damage
     */
    public void setAd(int pAd) {
        ad = pAd;
    }

    /**
     * @return Returns the name of the Character
     */
    public String getName() {
        return name;
    }

    /**
     * The Character is dealt damage based on the attack damage passed as a
     * parameter
     *
     * @param pAd Pass the Characters attack damage to deal damage
     */
    public void hit(int pAd) {
        hp -= pAd;
        if (isDead()) {
            die();
        }
    }

    /**
     * Prints a message to the Player that the Character died
     */
    public void die() {
        System.out.println(name + " died");
    }

    /**
     * @return Returns true if the Character is dead, means the Character has no
     * health points
     */
    public boolean isDead() {
        return hp <= 0;
    }
}