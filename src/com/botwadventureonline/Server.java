package com.botwadventureonline;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This class is the main class of the server. It contains the main method and
 * the commands.
 */
public class Server {

    private static final Map<String, Method> commands = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int autoSaveIntervalSeconds = 5;
    private static Thread autosaveThread;
    private static GameMap map;
    private static int mapWidth;
    private static int mapHeight;

    private static final SaveManager saveManager = new SaveManager();

    /**
     * Method to configure and start the game.
     */
    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(1337);

        addCommands();

        initiateGame();

        acceptClients(server);

        startAutoSave();

        if (mapHeight > 0 && mapWidth > 0) {
            map = new GameMap(mapWidth, mapHeight);
            map.randomizeHestu();
            play();
        }
    }

    public static void acceptClients(ServerSocket server) {
        Runnable acceptClient = () -> {
            // noinspection InfiniteLoopStatement
            while (true) {
                Socket client;
                try {
                    client = server.accept();
                    System.out.println("A new client has joined the game");
                    System.out.println("Client IP: " + client.getInetAddress().getHostAddress());

                    DataInputStream dis = new DataInputStream(client.getInputStream());
                    DataOutputStream dos = new DataOutputStream(client.getOutputStream());

                    String playerName = dis.readUTF();

                    System.out.println("Assigning new Thread for this client...");

                    Thread thread = new ClientThread(client, dis, dos, playerName);
                    thread.start();
                } catch (IOException e) {
                    System.out.println("An error occurred while accepting a client");
                }
            }
        };
        new Thread(acceptClient).start();

    }

    public static void startAutoSave() {
        Runnable autoSave = () -> {
            while (true) {
                try {
                    Thread.sleep(1000L * autoSaveIntervalSeconds);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Autosave interrupted");
                }
                saveManager.autoSave(map);
            }
        };

        autosaveThread = new Thread(autoSave);
        autosaveThread.start();
    }

    public static void play() throws InvocationTargetException, IllegalAccessException {
        String input;
        while (true) {
            System.out.print(">");
            input = scanner.next();
            if ("quit".equals(input)) {
                scanner.close();
                autosaveThread.interrupt();
                break;
            } else {
                if (commands.containsKey(input)) {
                    commands.get(input).invoke(null);
                } else {
                    System.out.println("You run around in circles and don't know what to do.");
                }
            }
        }
    }

    public static GameMap getMap() {
        return map;
    }

    public static int getHeight() {
        return map.getHeight();
    }

    public static int getWidth() {
        return map.getWidth();
    }

    @SuppressWarnings("CommentedOutCode")
    public static void addCommands() {
        try {
            // addCommand("help", "printHelp");
            // addCommand("forward", "forward");
            // addCommand("backward", "backward");
            // addCommand("right", "right");
            // addCommand("left", "left");
            // addCommand("look", "printState");
            // addCommand("position", "cords");
            // addCommand("stats", "stats");
            // addCommand("inventory", "printInventory");
            // addCommand("dig", "dig");
            // addCommand("rest", "rest");
            // addCommand("pickup", "pickUp");
            // addCommand("equip", "equip");
            // addCommand("drop", "drop");
            // addCommand("save", "saveGame");
            // addCommand("load", "loadGame");
            addCommand("setInterval", "setInterval");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setInterval() {
        System.out.println("Please enter the new interval in seconds:");
        autoSaveIntervalSeconds = scanner.nextInt();
        System.out.println("The new interval is " + autoSaveIntervalSeconds + " seconds.");
    }

    public static void initiateGame() {
        System.out.println("Welcome to the new game!");
        System.out.println("Please enter the height of the map:");
        mapHeight = scanner.nextInt();
        System.out.println("Please enter the width of the map:");
        mapWidth = scanner.nextInt();
        double difficulty = setDifficulty();
    }

    public static void saveGame() {
        saveManager.saveGame(map);
    }

    public static double setDifficulty() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("Choose a difficulty:");
        System.out.println("1. Super easy");
        System.out.println("2. Easy");
        System.out.println("3. Medium");
        System.out.println("4. Hard");
        System.out.println("5. Master mode");
        choice = scanner.nextInt();
        scanner.close();
        if (choice == 1) {
            return 3;
        } else if (choice == 2) {
            return 2;
        } else if (choice == 3) {
            return 1;
        } else if (choice == 4) {
            return 0.5;
        } else if (choice == 5) {
            return 0;
        } else {
            System.out.println("Invalid choice.");
            System.exit(1);
        }
        return -1;
    }

    public static void printHelp() {
        System.out.println("All commands: \n");
        for (Map.Entry<String, Method> entry : commands.entrySet()) {
            System.out.println(entry.getKey());
        }
    }

    public static void addCommand(String pCommand, String pMethodName) {
        try {
            commands.put(pCommand, Server.class.getMethod(pMethodName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setMap(GameMap pMap) {
        map = pMap;
    }

    public static Field getField(int playerX, int playerY) {
        return map.getField(playerX, playerY);
    }
}
