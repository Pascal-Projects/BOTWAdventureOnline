package com.botwAdventureOnline;


import java.io.*;

public class SaveManager extends Thread {

    public SaveManager() {
        super("AutosaveManager");
    }

    /**
     * Saves the current game state to a file every time the player did something
     *
     * @param player The player
     * @param map    The map
     */
    public static void autoSave(Player player, GameMap map) {
        try {
            FileOutputStream fos = new FileOutputStream(".\\src\\com\\botwAdventureOnline\\saves\\save.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(player);
            oos.writeObject(map);

            fos.close();
            oos.close();
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }

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


    /**
     * Loads the current game state from a file
     */
    public static void loadGame() {
        try {
            FileInputStream fis = new FileInputStream(".\\src\\com\\botwAdventureOnline\\saves\\save.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            Player player = (Player) ois.readObject();
            GameMap map = (GameMap) ois.readObject();

            Main.setPlayer(player);
            Main.setMap(map);

            fis.close();
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
