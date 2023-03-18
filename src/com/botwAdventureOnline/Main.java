package com.botwAdventureOnline;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


@SuppressWarnings("unused")
public class Main {

    /*private static final Map<String, Method> commands = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final int autoSaveIntervalSeconds = 5;
    private static double difficulty = -1;
    private static Thread autosaveThread;
    private static Player player;
    private static Inventory inventory;
    private static GameMap map;
    private static String playerName;
    private static int mapWidth;
    private static int mapHeight;


    public static void main(String[] args) throws Exception {

        addCommands();

        initiateGame();

        startAutoSave();


        if (!"".equals(playerName) && mapHeight > 0 && mapWidth > 0) {
            map = new GameMap(mapWidth, mapHeight);
            player = new Player(playerName, 200, 100, 1, mapWidth / 2, mapHeight / 2);
            inventory = player.getInventory();
            map.addHestu(0, 0);
            map.addMasterSword();
            Random random = new Random();
            play();
        }
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
                SaveManager.autoSave(player, map);
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
            levelUp();
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
        System.out.println("Welcome to your new game!");
        System.out.println("Please enter your name:");
        playerName = scanner.nextLine();
        System.out.println("Welcome " + playerName + "!");
        System.out.println("Please enter the height of the map:");
        mapHeight = scanner.nextInt();
        System.out.println("Please enter the width of the map:");
        mapWidth = scanner.nextInt();
        difficulty = setDifficulty();
    }

    public static void saveGame() {
        SaveManager.saveGame(player, map);
    }

    public static void loadGame() {
        SaveManager.loadGame();
    }

    public static void drop() {
        if (inventory.getItemCount() > 0) {
            System.out.println("Would you like to drop a potion or a sword?");
            Scanner scanner = new Scanner(System.in);
            int choice;
            int counter = 1;
            if (scanner.next().equalsIgnoreCase("potion")) {
                if (inventory.getPotions().size() > 0) {
                    for (Item potion : inventory.getPotions()) {
                        if (potion.getName().equals("Health Potion")) {
                            System.out.println(counter + ". Health Potion (Health points: " + potion.getHealthPoints() + ", Value: " + potion.getValue() + " Rupees)");
                        } else if (potion.getName().equals("Experience Potion")) {
                            System.out.println(counter + ". Experience Potion (Experience points: " + potion.getExperiencePoints() + ", Value: " + potion.getValue() + " Rupees)");
                        }
                        counter++;
                    }
                    System.out.println("Which potion would you like to drop?\n>>>");
                    choice = scanner.nextInt() - 1;
                    if (0 <= choice && choice <= inventory.getPotions().size()) {
                        inventory.setWeight(inventory.getWeight() - inventory.getPotions().get(choice).getWeightInt());
                        inventory.removePotion(inventory.getPotions().get(choice));
                    } else {
                        System.out.println("Please select a potion");
                    }
                } else {
                    System.out.println("You don't have any potions in your inventory");
                }
            } else if (scanner.next().equalsIgnoreCase("sword")) {
                if (inventory.getSwords().size() > 0) {
                    for (Item sword : inventory.getSwords()) {
                        System.out.println(counter + ". " + sword.getName() + " (Damage: " + sword.getDamage() + ", Value: " + sword.getValue() + " Rupees)");
                        counter++;
                    }
                    choice = scanner.nextInt() - 1;
                    if (0 <= choice && choice < inventory.getSwords().size()) {
                        if (inventory.getSwords().get(choice) != inventory.getEquippedSword()) {
                            inventory.setWeight(inventory.getWeight() - inventory.getSwords().get(choice).getWeightInt());
                            inventory.removeSword(inventory.getSwords().get(choice));
                        } else {
                            System.out.println("You try to drop the sword you have equipped! Do you want to drop it anyway?\n >>>");
                            String answer = scanner.next();
                            if (answer.equalsIgnoreCase("yes")) {
                                inventory.setWeight(inventory.getWeight() - inventory.getSwords().get(choice).getWeightInt());
                                inventory.setEquippedSword(null);
                                player.setAd(player.getStartAd());
                                inventory.removeSword(inventory.getSwords().get(choice));
                            }
                        }
                    }
                } else {
                    System.out.println("You don't have any swords");
                }
            } else {
                System.out.println("Please choose what you want to drop");
            }
            scanner.close();
        } else {
            System.out.println("You don't have any items in your inventory");
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

    public static void pickUp() {
        if (map.getLoot(player).size() > 0) {
            int counter = 1;
            Scanner scanner = new Scanner(System.in);
            System.out.println("The items on this field:");
            for (Item item : map.getLoot(player)) {
                if (item.getName().equals("Experience Potion")) {
                    System.out.println(counter + ". " + item.getName() + " (Experience points: " + item.getExperiencePoints()
                            + ", Value: " + item.getValue() + " Rupees)");
                } else if (item.getName().equals("Health Potion")) {
                    System.out.println(counter + ". " + item.getName() + " (Health points: " + item.getHealthPoints() + ", Value: "
                            + item.getValue() + " Rupees)");
                } else {
                    System.out.println(counter + ". " + item.getName() + " (Damage: " + item.getDamage() + ", Value: "
                            + item.getValue() + " Rupees)");
                }
                counter++;
            }
            System.out.println("What is the Item you would like to pickup?\n >>>");
            int choice = scanner.nextInt();
            if (choice == 0) {
                System.out.println("Please select a item");
            } else {
                if (0 < choice && choice <= map.getLoot(player).size()) {
                    if (inventory.getWeight() + map.getLoot(player).get(choice - 1).getWeightInt() <= inventory.getMaxWeight()) {
                        String itemName = map.getLoot(player).get(choice - 1).getName();
                        if (itemName.equals("Experience Potion") || itemName.equals("Health Potion")) {
                            inventory.addPotion(map.getLoot(player).get(choice - 1));
                            inventory.setWeight(inventory.getWeight() + map.getLoot(player).get(choice - 1).getWeightInt());
                            map.removeLoot(player, map.getLoot(player).get(choice - 1));
                        } else {
                            if (itemName.equals("Master Sword")) {
                                if (player.getHp() >= 300) {
                                    inventory.addSword(map.getLoot(player).get(choice - 1));
                                    inventory.setWeight(inventory.getWeight() + map.getLoot(player).get(choice - 1).getWeightInt());
                                    map.removeLoot(player, map.getLoot(player).get(choice - 1));
                                } else {
                                    System.out.println("You are not yet strong enough to pick up the Master Sword!");
                                }
                            } else {
                                inventory.addSword(map.getLoot(player).get(choice - 1));
                                inventory.setWeight(inventory.getWeight() + map.getLoot(player).get(choice - 1).getWeightInt());
                                map.removeLoot(player, map.getLoot(player).get(choice - 1));
                            }
                        }
                    } else {
                        System.out.println("You don't have enough space in your inventory!");
                    }
                } else {
                    System.out.println("There is no item with that number!");
                }
            }
        } else {
            System.out.println("There is no item on this field!");
        }
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

    public static void fight() {
        ArrayList<Monster> enemies = map.getEnemies(player);
        while (enemies.size() > 0) {
            enemies.get(0).hit(player.getAd());
            if (enemies.get(0).isDead()) {
                player.setEp(player.getEp() + enemies.get(0).getEp());
                map.addLoot(player, enemies.get(0).getLoot());
                enemies.remove(0);
            }
            for (int i = 0; i < enemies.size(); i++) {
                double rand = random.nextDouble(20 / difficulty);
                if (rand == 1) {
                    System.out.println("Attack from " + enemies.get(i).getName() + " blocked!");
                    if (enemies.get(i).getName().equals("Guardian")) {
                        enemies.get(i).die();
                        player.setEp(player.getEp() + enemies.get(i).getEp());
                        map.addLoot(player, enemies.get(i).getLoot());
                        //noinspection SuspiciousListRemoveInLoop
                        enemies.remove(i);
                    }
                } else {
                    int damage = enemies.get(i).getAd() / player.getDp();
                    player.hit(damage);
                    System.out.println("Attack from " + enemies.get(i).getName() + "! You lost " + damage + " health points!");
                }
            }
        }
        if (player.getHp() != player.getMaxHp()) {
            System.out.println("You won the fight but you only have " + player.getHp() + " health points left!");
        } else {
            System.out.println("You won without losing any health points!");
        }
    }

    public static void dig() {
        map.dig(player);
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

    public static void korok() {
        if (map.hasKorok(player)) {
            int num1;
            int num2;
            Scanner scanner = new Scanner(System.in);
            String answer;
            System.out.println("There is a korok here!");
            System.out.println("Solve the Puzzle from the korok to get a korokseed!");
            num1 = random.nextInt(1000);
            num2 = random.nextInt(1000);
            System.out.println("What is the sum of " + num1 + " and " + num2 + "?\n >>>");
            answer = scanner.next();
            if (answer.equals(Integer.toString(num1 + num2))) {
                System.out.println("Correct!");
                System.out.println("You received a korokseed!");
                inventory.setKorokSeeds(inventory.getKorokSeeds() + 1);
                map.removeKorok(player);
            } else {
                System.out.println("Incorrect!");
                System.out.println("The Korok laughs at you and runs away!");
            }
        } else {
            System.out.println("There is no korok here!");
        }
    }

    public static void hestu() {
        if (map.getHestu(player)) {
            Scanner scanner = new Scanner(System.in);
            String input;
            if (inventory.getKorokSeeds() > 0) {
                if (Hestu.getHestuUsed() <= 2) {
                    if (inventory.getKorokSeeds() >= 1) {
                        System.out.println("Do you want to increase the maximum weight of your inventory for one korokseed?\n >>>");
                        input = scanner.next();
                        if ("yes".equalsIgnoreCase(input)) {
                            inventory.setMaxWeight(inventory.getMaxWeight() + 1);
                            inventory.setKorokSeeds(inventory.getKorokSeeds() - 1);
                            Hestu.setHestuUsed(Hestu.getHestuUsed() + 1);
                        }
                    } else {
                        System.out.println("You don't have enough korokseeds to increase the maximum weight of your inventory.");
                    }
                } else {
                    if (inventory.getKorokSeeds() >= (Hestu.getHestuUsed() - 1)) {
                        System.out.println("Do you want to increase the maximum weight of your inventory for " + (Hestu.getHestuUsed() - 1) + " korokseeds?\n >>>");
                        input = scanner.next();
                        if ("yes".equalsIgnoreCase(input)) {
                            inventory.setMaxWeight(inventory.getMaxWeight() + 1);
                            inventory.setKorokSeeds(inventory.getKorokSeeds() - (Hestu.getHestuUsed() - 1));
                            Hestu.setHestuUsed(Hestu.getHestuUsed() + 1);
                        }
                    } else {
                        System.out.println("You don't have enough korokseeds to increase the maximum weight of your inventory. Find koroks to get korokseeds");
                    }
                }
            }
        } else {
            System.out.println("Hestu is not located on this field. Search for him on another field");
        }
    }

    public static void levelUp() {
        player.levelUp();
    }

    public static void sell() {
        map.sell(player, inventory);
    }

    public static void buy() {
        map.buy(player, inventory);
    }

    public static void cords() {
        map.getCords(player);
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

    public static void printState() {
        map.printState(player);
    }

    public static void forward() {
        map.forward(player);
    }

    @SuppressWarnings("unused")
    public static void backward() {
        map.backward(player);
    }

    public static void right() {
        map.right(player);
    }

    public static void left() {
        map.left(player);
    }

    public static void rest() {
        player.rest();
    }

    public static void setPlayer(Player pPlayer) {
        player = pPlayer;
    }

    public static void setMap(GameMap pMap) {
        map = pMap;
    }*/
}
