package com.botwAdventureOnline;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@SuppressWarnings("CommentedOutCode")
public class SaveManager extends Thread {

    public SaveManager() {
        super("AutosaveManager");
    }

    /**
     * Save the current game state
     *
     * @param player The player
     * @param map    The map
     */
    public static void saveGame(Player player, GameMap map) {
        try {
            FileOutputStream fos = new FileOutputStream(".\\src\\com\\botwAdventureOnline\\saves\\save.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(player);
            oos.writeObject(map);

            fos.close();
            oos.close();
            System.out.println("Saved!");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }


    public static void autoSave(GameMap map) {
        try {
            FileOutputStream fos = new FileOutputStream(".\\src\\com\\botwAdventureOnline\\saves\\save.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(map);

            fos.close();
            oos.close();
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    public static void saveGame(GameMap map) {
        try {
            FileOutputStream fos = new FileOutputStream(".\\src\\com\\botwAdventureOnline\\saves\\save.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(map);

            fos.close();
            oos.close();
            System.out.println("Saved!");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }
}
