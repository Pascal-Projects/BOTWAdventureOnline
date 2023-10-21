package com.botwadventureonline;

/**
 * @extends Character
 * @implements Serializable
 */
public class Player extends Character{
    private static final long serialVersionUID = 1L;

    /**
     * The X coordinate of the player
     */
    private int xCoordinate;

    /**
     * The Y coordinate of the player
     */
    private int yCoordinate;

    /**
     * The player's inventory
     */
    private final Inventory inventory;

    /**
     * Defines the maximum health of the player.
     */
    private int maxHP;

    /**
     * Defines the current experience points of the player.
     */
    private int ep = 0;

    /**
     * Defense Points of the player
     */
    private final int dp;

    /**
     * The list with experience points needed for a new level
     */
    private final int[] levelList;

    /**
     * Defines the current level of the player.
     */
    private int level = 0;

    /**
     * Defines the player's attack damage at the start of the game
     */
    private int startAd;

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setXCoordinate(int pxCoordinate) {
        xCoordinate = pxCoordinate;
    }

    public void setYCoordinate(int pyCoordinate) {
        yCoordinate = pyCoordinate;
    }

    /**
     * @return Returns the player's level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return Returns the player's defense points
     */
    public int getDp() {
        return dp;
    }

    /**
     * @return Returns the maximal health of the player
     */
    public int getMaxHp() {
        return maxHP;
    }

    /**
     * @return Returns the player's experience points
     */
    public int getEp() {
        return ep;
    }

    /**
     * Updates the player's experience points
     *
     * @param ep The new player's experience points
     */
    public void setEp(int ep) {
        this.ep = ep;
    }

    /**
     * @param pName The name of the player
     * @param pHp   The maximal health of the player at the start of the game
     * @param pAd   The player's attack damage at the start of the game
     * @param pDp   The player's defense points at the start of the game
     */
    public Player(String pName, int pHp, int pAd, int pDp, int pXCoordinate, int pYCoordinate) {
        super(pHp, pAd, pName);
        maxHP = pHp;
        startAd = pAd;
        dp = pDp;
        inventory = new Inventory(10);
        levelList = new int[]{10, 25, 50, 75, 100, 200, 300, 400, 500, 600};
        xCoordinate = pXCoordinate;
        yCoordinate = pYCoordinate;
    }

    /**
     * Runs if the player is dead because he has no more health points
     */
    @Override
    public void die() {
        System.out.println("You died");
        System.exit(0);
    }

    /**
     * Sets the player's level health points to his maximum health
     */
    public void rest() {
        setHp(maxHP);
    }

    /**
     * @return Returns if the player has enough experience points to level up
     */
    public boolean nextLevel() {
        return ep >= levelList[level];
    }

    /**
     * Levels up the player and increases the player's stats
     */
    public void levelUp() {
        while (nextLevel()) {
            ep -= levelList[level];
            level++;
            System.out.println("Level Up!");
            maxHP += 15;
            setAd(getAd() + 5);
            setStartAd(getStartAd() + 5);
            System.out.println("Your new level is: " + level);
        }
    }

    /**
     * @param i the new start attack damage
     */
    private void setStartAd(int i) {
        startAd = i;
    }

    /**
     * @return Returns the player's inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * @return Returns the player's start attack damage
     */
    public int getStartAd() {
        return startAd;
    }
}
