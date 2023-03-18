package com.botwAdventureOnline;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.*;

@SuppressWarnings("unused")
public class Client {

    private static final Map<String, Method> commandsLocal = new HashMap<>();
    private static final Map<String, Method> commandsRemote = new HashMap<>();
    private static Player player;
    private static Inventory inventory;

    private static int mapHeight;

    private static int mapWidth;

    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {

        addCommands();


        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your name:");
        String playerName = scanner.nextLine();
        System.out.println("Welcome " + playerName + "!");

        Random random = new Random();

        String input;

        String ip = "87.78.179.241";

        Socket connection = new Socket(ip, 1337);

        DataInputStream dis = new DataInputStream(connection.getInputStream());
        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

        System.out.println(dis.readUTF());

        mapHeight = dis.readInt();

        mapWidth = dis.readInt();

        player = new Player(playerName, 200, 100, 1, random.nextInt(mapHeight), random.nextInt(mapWidth));

        inventory = player.getInventory();

        while (true) {
            System.out.print(">");
            input = scanner.next();
            if ("quit".equals(input)) {
                scanner.close();
                break;
            } else {
                if (commandsLocal.containsKey(input)) {
                    commandsLocal.get(input).invoke(null);
                } else if (commandsRemote.containsKey(input)) {
                    dos.writeUTF(input);
                    commandsRemote.get(input).invoke(null);
                } else {
                    System.out.println("You run around in circles and don't know what to do.");
                }
            }
            levelUp();
        }
    }

    private static void addCommands() {
        try {
            addLocalCommand("levelup", "levelUp");
            addLocalCommand("help", "printHelp");
            addLocalCommand("stats", "stats");
            addLocalCommand("inventory", "printInventory");
            addLocalCommand("dig", "dig");
            addLocalCommand("rest", "rest");
            addLocalCommand("equip", "equip");
            addLocalCommand("drink", "drink");
            addLocalCommand("position", "cords");




            addRemoteCommand("forward", "forward");
            addRemoteCommand("backward", "backward");
            addRemoteCommand("right", "right");
            addRemoteCommand("left", "left");
            addRemoteCommand("drop", "drop");
            addRemoteCommand("fight", "fight");
            addRemoteCommand("look", "printState");
            addRemoteCommand("sell", "sell");
            addRemoteCommand("buy", "buy");
            addRemoteCommand("hestu", "hestu");
            addRemoteCommand("korok", "korok");
            addRemoteCommand("pickup", "pickUp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addLocalCommand(String command, String method) throws NoSuchMethodException {
        commandsLocal.put(command, Client.class.getMethod(method));
    }

    private static void addRemoteCommand(String command, String method) throws NoSuchMethodException {
        commandsRemote.put(command, Client.class.getMethod(method));
    }

    public static void levelUp() {
        player.levelUp();
    }

    public static void printHelp() {
        System.out.println("All commands: \n");
        for (Map.Entry<String, Method> entry : commandsLocal.entrySet()) {
            System.out.println(entry.getKey());
        }
        for (Map.Entry<String, Method> entry : commandsRemote.entrySet()) {
            System.out.println(entry.getKey());
        }
    }

    public static void stats() {
        System.out.println("\nStats for " + player.getName() + ":\n");
        System.out.println("Health: " + player.getHp() + "/" + player.getMaxHp());
        System.out.println("Attack Damage: " + player.getAd());
        System.out.println("Defense Points: " + player.getDp());
        System.out.println("Experience Points: " + player.getEp());
        System.out.println("Level: " + player.getLevel() + "\n");
        System.out.println("Position: ");
        System.out.println("X: " + player.getXCoordinate());
        System.out.println("Y: " + player.getYCoordinate() + "\n");
        System.out.println("Inventory Usage: " + inventory.getUsage() + "%");
        System.out.println("Korokseeds: " + inventory.getKorokSeeds());
        System.out.println("Rupees: " + inventory.getRupees() + "\n");
    }

    public static void printInventory() {
        System.out.println("\nInventory of " + player.getName() + ":");
        for (Item potion : inventory.getPotions()) {
            if (potion.getName().equals("Health Potion")) {
                System.out.println("Health Potion (Health points: " + potion.getHealthPoints() + ", Value: " + potion.getValue() + " Rupees)");
            } else if (potion.getName().equals("Experience Potion")) {
                System.out.println("Experience Potion (Experience points: " + potion.getExperiencePoints() + ", Value: " + potion.getValue() + " Rupees)");
            }
        }
        for (Item sword : inventory.getSwords()) {
            System.out.println(sword.getName() + " (Damage: " + sword.getDamage() + ", Value: " + sword.getValue() + " Rupees)");
        }
        System.out.println('\n' +
                "Total weight: " + inventory.getWeight() + " (" + inventory.getUsage() + "%)");
    }

    public static void dig() {
        Random random = new Random();
        player.setXCoordinate(random.nextInt(mapWidth) - 1);
        player.setYCoordinate(random.nextInt(mapHeight) - 1);
        //printState(); TODO: PrintState()
    }

    public static void rest() {
        player.rest();
    }

    public static void equip() {
        if (inventory.getSwords().size() > 0) {
            int counter = 1;
            Scanner scanner = new Scanner(System.in);
            int choice;
            System.out.println("You swords:");
            for (Item sword : inventory.getSwords()) {
                System.out.println(counter + ". " + sword.getName() + " (Damage: " + sword.getDamage() + ")");
                counter++;
            }
            System.out.print("Which sword do you want to equip\n >>>");
            choice = scanner.nextInt() - 1;
            if (0 <= choice && choice <= inventory.getSwords().size()) {
                player.setAd(inventory.getSwords().get(choice).getDamageInt());
                inventory.setEquippedSword(inventory.getSwords().get(choice));
            } else {
                System.out.println("Please select a sword.");
            }
        } else {
            System.out.println("You don't have any swords to equip.");
        }
    }

    public static void drink() {
        if (inventory.getPotions().size() > 0) {
            int counter = 1;
            Scanner scanner = new Scanner(System.in);
            int choice;
            ArrayList<Item> potionList = inventory.getPotions();
            System.out.println("Your Potions:");
            for (Item potion : potionList) {
                if (potion.getName().equals("Experience Potion")) {
                    System.out.println(counter + ". " + potion.getName() + "(Health Points: " + potion.getHealthPoints() + ")");
                } else if (potion.getName().equals("Health Potion")) {
                    System.out.println(counter + ". " + potion.getName() + "(Health Points: " + potion.getHealthPoints() + ")");
                }
                counter++;
            }
            System.out.print("Which is the Potion you want to drink?\n>>>");
            choice = scanner.nextInt() - 1;
            if (0 <= choice && choice < potionList.size()) {
                if (potionList.get(choice).getName().equals("Experience Potion")) {
                    player.setEp(player.getEp() + potionList.get(choice).getHealthPointsInt());
                    inventory.setWeight(inventory.getWeight() - potionList.get(choice).getWeightInt());
                    inventory.removePotion(potionList.get(choice));
                } else if (potionList.get(choice).getName().equals("Health Potion")) {
                    if (player.getHp() + potionList.get(choice).getHealthPointsInt() <= player.getMaxHp()) {
                        player.setHp(player.getHp() + potionList.get(choice).getHealthPointsInt());
                        inventory.setWeight(inventory.getWeight() - potionList.get(choice).getWeightInt());
                        inventory.removePotion(potionList.get(choice));
                    } else {
                        System.out.println("You would waste " + (player.getHp() + potionList.get(choice).getExperiencePointsInt() - player.getMaxHp()) + " health points");
                        System.out.println("Continue anyway?");
                        String answer = scanner.next();
                        if (answer.equalsIgnoreCase("yes")) {
                            player.setHp(player.getMaxHp());
                            inventory.setWeight(inventory.getWeight() - potionList.get(choice).getWeightInt());
                            inventory.removePotion(potionList.get(choice));
                        }
                    }
                }
            } else {
                System.out.println("Please select a potion");
            }
        } else {
            System.out.println("You don't have any potions in your inventory");
        }
    }

    public static void cords() {
        System.out.println("Your coordinates are X: " + player.getXCoordinate() + ", Y: " + player.getYCoordinate());
    }
}

