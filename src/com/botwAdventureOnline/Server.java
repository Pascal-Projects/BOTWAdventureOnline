package com.botwAdventureOnline;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


@SuppressWarnings("unused")
public class Server {

    private static final Map<String, Method> commands = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final int autoSaveIntervalSeconds = 5;
    private static double difficulty = -1;
    private static Thread autosaveThread;
    private static GameMap map;
    private static int mapWidth;
    private static int mapHeight;

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
            map.addHestu(0, 0);
            Random random = new Random();
            play();
        }
    }

    public static void acceptClients(ServerSocket server) {
        Runnable acceptClient = () -> {
            while (true) {
                Socket client;
                try {
                    client = server.accept();
                    System.out.println("A new client has joined the game: " + client);
                    System.out.println("Client IP: " + client.getInetAddress().getHostAddress());
                    System.out.println("Waiting for commands...");

                    DataInputStream dis = new DataInputStream(client.getInputStream());
                    DataOutputStream dos = new DataOutputStream(client.getOutputStream());

                    System.out.println("Assigning new Thread for this client...");

                    Thread thread = new ClientThread(client, dis, dos);
                    thread.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        new Thread(acceptClient).start();

    }

    public static void startAutoSave() {
        Runnable autoSave = () -> {
            //noinspection InfiniteLoopStatement
            while (true) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(1000L * autoSaveIntervalSeconds);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                SaveManager.autoSave(map);
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
                checkHestu();
            }
        }
    }

    public static void checkHestu() {
        if (Hestu.getHestuUsed() > 2 && !Hestu.isHestuMoved()) {
            map.removeHestu();
            map.addHestu(random.nextInt(mapWidth) - 1, random.nextInt(mapHeight) - 1);
            Hestu.setHestuMoved(true);
        }
    }

    public static void addCommands() {
        try {
            addCommand("help", "printHelp");
            addCommand("forward", "forward");
            addCommand("backward", "backward");
            addCommand("right", "right");
            addCommand("left", "left");
            addCommand("look", "printState");
            addCommand("position", "cords");
            addCommand("sell", "sell");
            addCommand("buy", "buy");
            addCommand("levelup", "levelUp");
            addCommand("hestu", "hestu");
            addCommand("korok", "korok");
            addCommand("stats", "stats");
            addCommand("inventory", "printInventory");
            addCommand("dig", "dig");
            addCommand("rest", "rest");
            addCommand("fight", "fight");
            addCommand("pickup", "pickUp");
            addCommand("equip", "equip");
            addCommand("drink", "drink");
            addCommand("drop", "drop");
            addCommand("save", "saveGame");
            addCommand("load", "loadGame");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initiateGame() {
        System.out.println("Welcome to the new game!");
        System.out.println("Please enter the height of the map:");
        mapHeight = scanner.nextInt();
        System.out.println("Please enter the width of the map:");
        mapWidth = scanner.nextInt();
        difficulty = setDifficulty();
    }

    public static void saveGame() {
        SaveManager.saveGame(map);
    }

    public static void loadGame() {
        SaveManager.loadGame();
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

    public static void addCommand(String pCommand, String pMethodName) throws Exception {
        commands.put(pCommand, Main.class.getMethod(pMethodName));
    }

    public static void setMap(GameMap pMap) {
        map = pMap;
    }
}
