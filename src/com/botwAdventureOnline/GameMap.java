package com.botwAdventureOnline;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@SuppressWarnings("unused")
public class GameMap implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * An object to generate random numbers.
     */
    private static final Random random = new Random();
    /**
     * The width of the map
     */
    private final int width;
    /**
     * The height of the map
     */
    private final int height;
    /**
     * The final map of the game.
     */
    private Field[][] karteFull;

    /**
     * @param pWidth  Sets the width of the map
     * @param pHeight Sets the height of the map
     */
    public GameMap(int pWidth, int pHeight) {
        ArrayList<Field[]> karte3d = new ArrayList<>();
        ArrayList<Field> fields = new ArrayList<>();
        height = pHeight;
        width = pWidth;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addLoot(Player player, Item item) {
        karteFull[player.getXCoordinate()][player.getYCoordinate()].addLoot(item);
    }

    public boolean hasKorok(Player player) {
        return karteFull[player.getXCoordinate()][player.getYCoordinate()].hasKoroks();
    }

    public void removeKorok(Player player) {
        karteFull[player.getXCoordinate()][player.getYCoordinate()].removeKorok();
    }

    /**
     * @return Returns true if the field of the player's current position contains Hestu
     */
    public boolean getHestu(Player player) {
        return karteFull[player.getXCoordinate()][player.getYCoordinate()].getHestu() != null;
    }

    public void printState(Player player) {
        karteFull[player.getXCoordinate()][player.getYCoordinate()].printState();
    }

    /**
     * Moves the player up to next field and has a 10% chance of adding a korok to the field
     */
    public void forward(Player player) {
        if (player.getXCoordinate() == height - 1) {
            System.out.println("You see huge mountains, which you can't pass");
        } else {
            player.setXCoordinate(player.getXCoordinate() + 1);
        }
        int rand = random.nextInt(10);
        if (rand == 1) {
            karteFull[player.getXCoordinate()][player.getYCoordinate()].addKorok();
        }
    }

    /**
     * Moves the player back to next field and has a 10% chance of adding a korok to the field
     */
    public void backward(Player player) {
        if (player.getXCoordinate() == 0) {
            System.out.println("You see cliffs, but you can't jump safely");
        } else {
            player.setXCoordinate(player.getXCoordinate() - 1);
        }
        int rand = random.nextInt(10);
        if (rand == 1) {
            karteFull[player.getXCoordinate()][player.getYCoordinate()].addKorok();
        }
    }

    /**
     * Moves the player right to next field and has a 10% chance of adding a korok to the field
     */
    public void right(Player player) {
        if (player.getYCoordinate() == width - 1) {
            System.out.println("You see huge mountains, which you can't pass");
        } else {
            player.setYCoordinate(player.getYCoordinate() + 1);
        }
        int rand = random.nextInt(10);
        if (rand == 1) {
            karteFull[player.getXCoordinate()][player.getYCoordinate()].addKorok();
        }
    }

    /**
     * Moves the player left to next field and has a 10% chance of adding a korok to the field
     */
    public void left(Player player) {
        if (player.getYCoordinate() == 0) {
            System.out.println("You see cliffs, but you can't jump safely");
        } else {
            player.setYCoordinate(player.getYCoordinate() - 1);
        }
        int rand = random.nextInt(10);
        if (rand == 1) {
            karteFull[player.getXCoordinate()][player.getYCoordinate()].addKorok();
        }
    }

    public ArrayList<Monster> getEnemies(Player player) {
        return karteFull[player.getXCoordinate()][player.getYCoordinate()].getMonsters();
    }

    /**
     * Prints out the player's current position
     */
    public void getCords(Player player) {
        System.out.println("Your coordinates are X: " + player.getXCoordinate() + ", Y: " + player.getYCoordinate());
    }

    public void sell(Player player, Inventory inventory) {
        karteFull[player.getXCoordinate()][player.getYCoordinate()].sell(inventory);
    }

    public void buy(Player player, Inventory inventory) {
        karteFull[player.getXCoordinate()][player.getYCoordinate()].buy(inventory);
    }

    public void addMasterSword() {
        karteFull[width - 1][height - 1].addMasterSword();
    }

    /**
     * Sets the player's position to a random position
     */
    public void dig(Player player) {
        player.setXCoordinate(random.nextInt(width) - 1);
        player.setYCoordinate(random.nextInt(height) - 1);
        karteFull[player.getXCoordinate()][player.getYCoordinate()].printState();
    }

    public ArrayList<Item> getLoot(Player player) {
        return karteFull[player.getXCoordinate()][player.getYCoordinate()].getLoot();
    }

    public void removeLoot(Player player, Item item) {
        karteFull[player.getXCoordinate()][player.getYCoordinate()].removeLoot(item);
    }

    public Field getField(int playerX, int playerY) {
        return karteFull[playerX][playerY];
    }

    public void randomizeHestu() {
        karteFull[random.nextInt(width - 1)][random.nextInt(height - 1)].addHestu();
    }
}