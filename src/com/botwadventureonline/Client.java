package com.botwadventureonline;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.*;

public class Client {

    private static final Map<String, Method> commandsLocal = new HashMap<>();
    private static final Map<String, Method> commandsRemote = new HashMap<>();
    private static Player player;
    private static Inventory inventory;

    private static int mapHeight;
    private static int mapWidth;

    private static DataInputStream dis;
    private static DataOutputStream dos;

    private static Socket connection;

    private static final String HEALTH_POTION = "Health Potion";
    private static final String EXPERIENCE_POTION = "Experience Potion";
    private static final String RUPEES = " Rupees)";
    private static final String VALUE = ", Value: ";

    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {

        addCommands();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your name:");
        String playerName = scanner.nextLine();
        System.out.println("Welcome " + playerName + "!");

        Random random = new Random();

        String input;

        String ip = "87.78.179.241";

        try {
            connection = new Socket(ip, 1337);
        } catch (Exception e) {
            System.out.println("Could not connect to server");
            System.exit(0);
        }

        dis = new DataInputStream(connection.getInputStream());
        dos = new DataOutputStream(connection.getOutputStream());

        dos.writeUTF(playerName);

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
        connection.close();
    }

    private static void addCommands() {
        // noinspection CommentedOutCode
        try {

            addRemoteCommand("ping", "ping");

            addLocalCommand("levelup", "levelUp");
            addLocalCommand("help", "printHelp");
            addLocalCommand("stats", "stats");
            addLocalCommand("inventory", "printInventory");
            addLocalCommand("dig", "dig");
            addLocalCommand("rest", "rest");
            addLocalCommand("equip", "equip");
            addLocalCommand("drink", "drink");
            addLocalCommand("position", "cords");

            addRemoteCommand("test", "test");
            addRemoteCommand("forward", "forward");
            addRemoteCommand("backward", "backward");
            addRemoteCommand("right", "right");
            addRemoteCommand("left", "left");
            addRemoteCommand("look", "printState");
            addRemoteCommand("hestu", "hestu");

            addRemoteCommand("korok", "korok");

            /*
             * TODO: Add Commands
             * addRemoteCommand("drop", "drop");
             * addRemoteCommand("fight", "fight");
             * addRemoteCommand("sell", "sell");
             * addRemoteCommand("buy", "buy");
             * addRemoteCommand("pickup", "pickUp");
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test() throws IOException {
        System.out.println("Running test");
        dos.writeUTF("Received command from: " + player.getName());
        System.out.println(dis.readUTF());
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
            if (potion.getName().equals(HEALTH_POTION)) {
                System.out.println(HEALTH_POTION + " (Health points: " + potion.getHealthPoints() + VALUE
                        + potion.getValue() + RUPEES);
            } else if (potion.getName().equals(EXPERIENCE_POTION)) {
                System.out.println("Experience Potion (Experience points: " + potion.getExperiencePoints() + VALUE
                        + potion.getValue() + RUPEES);
            }
        }
        for (Item sword : inventory.getSwords()) {
            System.out.println(
                    sword.getName() + " (Damage: " + sword.getDamage() + VALUE + sword.getValue() + RUPEES);
        }
        System.out.println('\n' +
                "Total weight: " + inventory.getWeight() + " (" + inventory.getUsage() + "%)");
    }

    private static final Random random = new Random();

    public static void dig() throws IOException {
        player.setXCoordinate(random.nextInt(mapWidth) - 1);
        player.setYCoordinate(random.nextInt(mapHeight) - 1);
        printState();
    }

    public static void rest() {
        player.rest();
    }

    public static void equip() {
        if (!inventory.getSwords().isEmpty()) {
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
            scanner.close();
        } else {
            System.out.println("You don't have any swords to equip.");
        }
    }

    public static void drink() {
        if (!inventory.getPotions().isEmpty()) {
            int counter = 1;
            Scanner scanner = new Scanner(System.in);
            int choice;
            List<Item> potionList = inventory.getPotions();
            System.out.println("Your Potions:");
            for (Item potion : potionList) {
                if (potion.getName().equals(EXPERIENCE_POTION)) {
                    System.out.println(
                            counter + ". " + potion.getName() + "(Experience Points: " + potion.getExperiencePoints() + ")");
                } else if (potion.getName().equals(HEALTH_POTION)) {
                    System.out.println(
                            counter + ". " + potion.getName() + "(Health Points: " + potion.getHealthPoints() + ")");
                }
                counter++;
            }
            System.out.print("Which is the Potion you want to drink?\n>>>");
            choice = scanner.nextInt() - 1;
            if (0 <= choice && choice < potionList.size()) {
                if (potionList.get(choice).getName().equals(EXPERIENCE_POTION)) {
                    player.setEp(player.getEp() + potionList.get(choice).getHealthPointsInt());
                    inventory.setWeight(inventory.getWeight() - potionList.get(choice).getWeightInt());
                    inventory.removePotion(potionList.get(choice));
                } else if (potionList.get(choice).getName().equals(HEALTH_POTION)) {
                    if (player.getHp() + potionList.get(choice).getHealthPointsInt() <= player.getMaxHp()) {
                        player.setHp(player.getHp() + potionList.get(choice).getHealthPointsInt());
                        inventory.setWeight(inventory.getWeight() - potionList.get(choice).getWeightInt());
                        inventory.removePotion(potionList.get(choice));
                    } else {
                        System.out.println("You would waste "
                                + (player.getHp() + potionList.get(choice).getExperiencePointsInt() - player.getMaxHp())
                                + " health points");
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
            scanner.close();
        } else {
            System.out.println("You don't have any potions in your inventory");
        }
    }

    public static void cords() {
        System.out.println("Your coordinates are X: " + player.getXCoordinate() + ", Y: " + player.getYCoordinate());
    }

    public static void forward() throws IOException {
        dos.writeInt(player.getXCoordinate());
        dos.writeInt(player.getYCoordinate());
        boolean forward = dis.readBoolean();
        if (forward) {
            player.setXCoordinate(player.getXCoordinate() + 1);
            printState();
        } else {
            System.out.println("You see huge mountains, which you can't pass");
        }
    }

    public static void backward() throws IOException {
        dos.writeInt(player.getXCoordinate());
        dos.writeInt(player.getYCoordinate());
        boolean backward = dis.readBoolean();
        if (backward) {
            player.setXCoordinate(player.getXCoordinate() - 1);
            printState();
        } else {
            System.out.println("You see cliffs, but you can't jump safely");
        }
    }

    public static void left() throws IOException {
        dos.writeInt(player.getXCoordinate());
        dos.writeInt(player.getYCoordinate());
        boolean left = dis.readBoolean();
        if (left) {
            player.setYCoordinate(player.getYCoordinate() - 1);
            printState();
        } else {
            System.out.println("You see huge mountains, which you can't pass");
        }
    }

    public static void right() throws IOException {
        dos.writeInt(player.getXCoordinate());
        dos.writeInt(player.getYCoordinate());
        boolean right = dis.readBoolean();
        if (right) {
            player.setYCoordinate(player.getYCoordinate() + 1);
            printState();
        } else {
            System.out.println("You see cliffs, but you can't jump safely");
        }
    }

    public static void printState() throws IOException {
        String message;
        dos.writeInt(player.getXCoordinate());
        dos.writeInt(player.getYCoordinate());
        message = dis.readUTF();
        System.out.println(message);
    }

    public static void hestu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        dos.writeInt(player.getXCoordinate());
        dos.writeInt(player.getYCoordinate());
        boolean hestu = dis.readBoolean();
        if (hestu) {
            if (inventory.getKorokSeeds() > 0) {
                System.out.println("Do you want to increase the maximum weight of your inventory for 1 korokseed?");
                String answer = scanner.next();
                if (answer.equals("yes")) {
                    inventory.setMaxWeight(inventory.getMaxWeight() + 1);
                    inventory.setKorokSeeds(inventory.getKorokSeeds() - 1);
                    dos.writeBoolean(true);
                }
            } else {
                System.out.println("You don't have any korokseeds");
            }
        } else {
            System.out.println("There is no Hestu on this field");
        }
        scanner.close();
    }

    public void korok() {
        // Todo: Koroks
    }

    public static void ping() throws IOException {
        long start = System.currentTimeMillis();
        dos.writeUTF("ping");
        dis.readUTF();
        long end = System.currentTimeMillis();
        System.out.println(end - start + " ms");
    }
}