package com.botwAdventureOnline;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * A Merchant is a character that can buy items from the player or sell potions to him
 *
 * @extends Character
 */
public class Merchant extends Character {
    /**
     * The stock of the merchant, which is a list of potions that the merchant can sell
     */
    private final ArrayList<Potion> stock = new ArrayList<>();

    public Merchant() {
        super(1, 0, "Beedle");
        Random random = new Random();
        int loop = random.nextInt(3);
        for (int i = 0; i < loop; i++) {
            int rand = random.nextInt(2);
            int randItem;
            randItem = random.nextInt(2);
            if (rand == 1) {
                if (randItem == 1) {
                    stock.add(new ExperiencePotion(25));
                } else {
                    stock.add(new ExperiencePotion(50));
                }
            } else {
                if (randItem == 1) {
                    stock.add(new HealthPotion(25));
                } else {
                    stock.add(new HealthPotion(50));
                }
            }
        }
    }

    /**
     * This method is called when the player wants to buy a potion
     *
     * @param inventory The inventory of the player
     */
    public void buyItem(Inventory inventory) {
        Scanner scanner = new Scanner(System.in);
        if (!stock.isEmpty()) {
            System.out.println("Which item would you like to buy?");
            int counter = 1;
            for (Item item : stock) {
                if (item.getName().equals("Experience Potion")) {
                    System.out.println(counter + ". " + item.getName() + " (Experience Points: " + item.getExperiencePoints() + ", Value: " + item.getValue() + " Rupees)");
                } else if (item.getName().equals("Health Potion")) {
                    System.out.println(counter + ". " + item.getName() + " (Health Points: " + item.getHealthPoints() + ", Value: " + item.getValue() + " Rupees)");
                }
                counter++;
            }
            System.out.println("What is the Item you would like to buy?");
            int choice = scanner.nextInt();
            if (choice == 0) {
                System.out.println("Please select a item");
            } else {
                if (0 < choice && choice <= stock.size()) {
                    if (inventory.getRupees() >= stock.get(choice - 1).getValueInt()) {
                        if (inventory.getWeight() + stock.get(choice - 1).getWeightInt() <= inventory.getMaxWeight()) {
                            inventory.addPotion(stock.get(choice - 1));
                            inventory.setWeight(inventory.getWeight() + stock.get(choice - 1).getWeightInt());
                            inventory.setRupees(inventory.getRupees() - stock.get(choice - 1).getValueInt());
                            stock.remove(choice - 1);
                        } else {
                            System.out.println("You don't have enough space in your inventory");
                        }
                    } else {
                        System.out.println("You don't have enough Rupees");
                    }
                } else {
                    System.out.println("Please choose a valid item");
                }
            }
        } else {
            System.out.println(getName() + " doesn't have any items");
        }
    }

    /**
     * This method is called when the player wants to sell an Item
     *
     * @param inventory The inventory of the player
     */
    public void sellItem(Inventory inventory) {
        String choice;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which item would you like to sell? (Sword/Potion)");
        choice = scanner.next();
        int item;
        if (choice.equalsIgnoreCase("potion")) {
            if (inventory.getPotions().size() > 0) {
                int counter = 1;
                for (Item potion : inventory.getPotions()) {
                    if (potion.getName().equals("Experience Potion")) {
                        System.out.println(counter + ". " + potion.getName() + " (Experience Points: " + potion.getExperiencePoints() + ", Value: " + potion.getValue() + " Rupees)");
                    } else if (potion.getName().equals("Health Potion")) {
                        System.out.println(counter + ". " + potion.getName() + " (Health Points: " + potion.getHealthPoints() + ", Value: " + potion.getValue() + "Rupees)");
                    }
                    counter++;
                }
                System.out.println("Which item would you like to sell?");
                item = scanner.nextInt();
                if (item == 0) {
                    System.out.println("Please select a item");
                } else {
                    if (0 < item && item <= inventory.getPotions().size()) {
                        inventory.setRupees(inventory.getRupees() + inventory.getPotions().get(item - 1).getValueInt());
                        inventory.setWeight(inventory.getWeight() - inventory.getPotions().get(item - 1).getWeightInt());
                        inventory.getPotions().remove(item - 1);
                    }
                }
            } else {
                System.out.println("You don't have any potions");
            }
        } else if (choice.equalsIgnoreCase("sword")) {
            if (inventory.getSwords().size() > 0) {
                int counter = 1;
                for (Item sword : inventory.getSwords()) {
                    System.out.println(counter + ". " + sword.getName() + " (Damage: " + sword.getDamage() + ", Value: " + sword.getValue() + ")");
                    counter++;
                }
                System.out.println("Which sword would you like to sell?");
                item = scanner.nextInt();
                if (item == 0) {
                    System.out.println("Please select a item");
                } else {
                    if (0 < item && item <= inventory.getSwords().size()) {
                        inventory.setRupees(inventory.getRupees() + inventory.getSwords().get(item - 1).getValueInt());
                        inventory.setWeight(inventory.getWeight() - inventory.getSwords().get(item - 1).getWeightInt());
                        inventory.getSwords().remove(item - 1);
                    } else {
                        System.out.println("You don't have any swords");
                    }
                }
            }
        }
    }
}
