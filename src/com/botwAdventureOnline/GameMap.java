package com.botwAdventureOnline;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameMap implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * The final map of the game.
     */
    private Field[][] karteFull;
    /**
     * An object to generate random numbers.
     */
    private static final Random random = new Random();

    /**
     * The X coordinate of the player
     */
    private int playerX;

    /**
     * The Y coordinate of the player
     */
    private int playerY;

    /**
     * The width of the map
     */
    private final int width;
    /**
     * The height of the map
     */
    private final int height;

    /**
     * @param pWidth  Sets the width of the map
     * @param pHeight Sets the height of the map
     */
    public GameMap(int pWidth, int pHeight) {
        ArrayList<Field[]> karte3d = new ArrayList<>();
        ArrayList<Field> fields = new ArrayList<>();
        height = pHeight;
        width = pWidth;
        playerX = width / 2;
        playerY = height / 2;
        for (int i = 0; i < pWidth; i++) {
            for (int j = 0; j < pHeight; j++) {
                Field field = Field.generateField();
                fields.add(field);
            }
            Field[] list = new Field[fields.size()];
            list = fields.toArray(list);
            karte3d.add(list);
            fields.clear();
        }
        karteFull = new Field[pWidth][pHeight];
        karteFull = karte3d.toArray(karteFull);
        System.out.println(Arrays.deepToString(karteFull));
    }

    /**
     * @return Returns the X coordinate of the player
     */
    public int getPlayerX() {
        return playerX;
    }

    /**
     * @return Returns the Y coordinate of the player
     */
    public int getPlayerY() {
        return playerY;
    }


    public void addLoot(Item item) {
        karteFull[playerX][playerY].addLoot(item);
    }

    public boolean hasKorok() {
        return karteFull[playerX][playerY].hasKoroks();
    }

    public void removeKorok() {
        karteFull[playerX][playerY].removeKorok();
    }

    /**
     * @return Returns true if the field of the player's current position contains Hestu
     */
    public boolean getHestu() {
        return karteFull[playerX][playerY].getHestu() != null;
    }

    public void printState() {
        karteFull[playerX][playerY].printState();
    }

    /**
     * Moves the player up to next field and has a 10% chance of adding a korok to the field
     */
    public void forward() {
        if (playerX == height - 1) {
            System.out.println("You see huge mountains, which you can't pass");
        } else {
            playerX++;
        }
        int rand = random.nextInt(10);
        if (rand == 1) {
            karteFull[playerX][playerY].addKorok();
        }
    }

    /**
     * Moves the player back to next field and has a 10% chance of adding a korok to the field
     */
    public void backward() {
        if (playerX == 0) {
            System.out.println("You see cliffs, but you can't jump safely");
        } else {
            playerX--;
        }
        int rand = random.nextInt(10);
        if (rand == 1) {
            karteFull[playerX][playerY].addKorok();
        }
    }

    /**
     * Moves the player right to next field and has a 10% chance of adding a korok to the field
     */
    public void right() {
        if (playerY == width - 1) {
            System.out.println("You see huge mountains, which you can't pass");
        } else {
            playerY++;
        }
        int rand = random.nextInt(10);
        if (rand == 1) {
            karteFull[playerX][playerY].addKorok();
        }
    }

    /**
     * Moves the player left to next field and has a 10% chance of adding a korok to the field
     */
    public void left() {
        if (playerY == 0) {
            System.out.println("You see cliffs, but you can't jump safely");
        } else {
            playerY--;
        }
        int rand = random.nextInt(10);
        if (rand == 1) {
            karteFull[playerX][playerY].addKorok();
        }
    }

    public ArrayList<Monster> getEnemies() {
        return karteFull[playerX][playerY].getMonsters();
    }

    /**
     * Prints out the player's current position
     */
    public void getCords() {
        System.out.println("Your coordinates are X: " + playerX + ", Y: " + playerY);
    }

    public void sell(Inventory inventory) {
        karteFull[playerX][playerY].sell(inventory);
    }

    public void buy(Inventory inventory) {
        karteFull[playerX][playerY].buy(inventory);
    }

    public void addHestu(int x, int y) {
        karteFull[x][y].addHestu();
    }

    public void removeHestu() {
        karteFull[0][0].removeHestu();
    }

    public void addMasterSword() {
        karteFull[width - 1][height - 1].addMasterSword();
    }

    /**
     * Sets the player's position to a random position
     */
    public void dig() {
        playerX = random.nextInt(width) - 1;
        playerY = random.nextInt(height) - 1;
        karteFull[playerX][playerY].printState();
    }

    public ArrayList<Item> getLoot() {
        return karteFull[playerX][playerY].getLoot();
    }

    public void removeLoot(Item item) {
        karteFull[playerX][playerY].removeLoot(item);
    }
}