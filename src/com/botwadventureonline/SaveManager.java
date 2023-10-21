package com.botwadventureonline;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * The SaveManager class is responsible for saving the current game state
 */
public class SaveManager {

    private FileOutputStream fos;
    private ObjectOutputStream oos;

    public SaveManager() {
        try {
            fos = new FileOutputStream(".\\src\\com\\botwAdventureOnline\\saves\\save.txt");
            oos = new ObjectOutputStream(fos);
        } catch (IOException e) {
            errorInitializing();
        }
    }

    /**
     * Save the current game state
     *
     * @param player The player
     * @param map    The map
     */
    public void saveGame(Player player, GameMap map) {
        try {
            oos.writeObject(player);
            oos.writeObject(map);

            fos.close();
            oos.close();
            System.out.println("Saved!");
        } catch (IOException e) {
            errorInitializing();
        }
    }

    public void autoSave(GameMap map) {
        try {
            oos.writeObject(map);

            fos.close();
            oos.close();
        } catch (IOException e) {
            errorInitializing();
        }
    }

    public void saveGame(GameMap map) {
        try {
            oos.writeObject(map);

            fos.close();
            oos.close();
            System.out.println("Saved!");
        } catch (IOException e) {
            errorInitializing();
        }
    }

    public static void errorInitializing() {
        System.out.println("Error initializing stream");
    }
}