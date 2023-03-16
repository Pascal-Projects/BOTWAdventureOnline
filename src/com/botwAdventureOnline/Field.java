package com.botwAdventureOnline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * A field contains loot, monsters, merchants and koroks
 */
public class Field implements Serializable {
    private static final Random random = new Random();
    /**
     * A list of monsters in the field
     */
    private final ArrayList<Monster> monsters;
    /**
     * The merchant in the field
     */
    private Merchant merchant;
    /**
     * A list of Items in the field
     */
    private final ArrayList<Item> loot = new ArrayList<>();
    /**
     * list of koroks in the field
     */
    private final ArrayList<Korok> koroks = new ArrayList<>();
    /**
     * The Hestu in the field
     */
    private Hestu hestu;

    /**
     * @param pMonsters Sets the monsters in the field
     * @param pMerchant Sets the merchant in the field
     * @param pLoot     Sets the loot in the field
     */
    public Field(ArrayList<Monster> pMonsters, Merchant pMerchant, ArrayList<Item> pLoot) {
        monsters = pMonsters;
        merchant = pMerchant;
        hestu = null;
        if (pLoot != null) {
            loot.addAll(pLoot);
        }
    }

    /**
     * @param pMonsters Sets the monsters in the field
     * @param pLoot     Sets the loot in the field
     */
    public Field(ArrayList<Monster> pMonsters, ArrayList<Item> pLoot) {
        monsters = pMonsters;
        hestu = null;
        if (pLoot != null) {
            loot.addAll(pLoot);
        }
    }

    /**
     * @return Returns the monsters in the field
     */
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    /**
     * @return Returns hestu
     */
    public Hestu getHestu() {
        return hestu;
    }

    /**
     * @return Returns the loot in the field
     */
    public ArrayList<Item> getLoot() {
        return loot;
    }

    /**
     * Adds an Item to the loot
     *
     * @param item The Item to add to the loot
     */
    public void addLoot(Item item) {
        loot.add(item);
    }

    /**
     * Removes an Item to the loot
     *
     * @param item The Item to remove to the loot
     */
    public void removeLoot(Item item) {
        loot.remove(item);
    }

    /**
     * @return Returns if the field contains koroks
     */
    public boolean hasKoroks() {
        return !koroks.isEmpty();
    }

    /**
     * Removes one korok from the field
     */
    public void removeKorok() {
        koroks.remove(0);
    }

    /**
     * Adds a korok to the field
     */
    public void addKorok() {
        koroks.add(new Korok());
    }

    /**
     * Adds Hestu to the field
     */
    public void addHestu() {
        hestu = new Hestu();
    }

    /**
     * Removes Hestu from the field
     */
    public void removeHestu() {
        hestu = null;
    }

    /**
     * Adds the Master Sword to the field
     */
    public void addMasterSword() {
        loot.add(new Sword("Master Sword", 2, 160));
    }

    /**
     * Lets you buy from the merchant if the field contains a merchant and has no monsters in it
     *
     * @param pInventory The player's inventory
     */
    public void buy(Inventory pInventory) {
        if (monsters.isEmpty()) {
            if (merchant != null) {
                merchant.buyItem(pInventory);
            } else {
                System.out.println("There is no merchant in this field.");
            }
        } else {
            System.out.println(merchant.getName()
                    + " is afraid of the monsters on this field and that's why he doesn't want to sell you anything");
        }
    }

    /**
     * Lets you sell to the merchant if the field contains a merchant and has no monsters in it
     *
     * @param pInventory The player's inventory
     */
    public void sell(Inventory pInventory) {
        if (monsters.isEmpty()) {
            if (merchant != null) {
                merchant.sellItem(pInventory);
            } else {
                System.out.println("There is no merchant in this field.");
            }
        } else {
            System.out.println(merchant.getName()
                    + " is afraid of the monsters on this field and that's why he doesn't want to buy anything from you.");
        }
    }

    /**
     * Prints everything the field contains
     */
    public void printState() {
        if (monsters.isEmpty() && merchant == null && loot.isEmpty() && koroks.isEmpty() && hestu == null) {
            System.out.println("You look around, but don't see anything interesting.");
        } else {
            System.out.println("You look around and see: \n");
            if (!monsters.isEmpty()) {
                System.out.println("Monsters: ");
                for (Character monster : monsters) {
                    System.out.println("• " + monster.getName());
                }
                System.out.println();
            }
            if (!loot.isEmpty()) {
                System.out.println("Loot: ");
                for (Item item : loot) {
                    if (item.getName().equals("Experience Potion")) {
                        System.out.println("• " + item.getName() + " (Experience points: " + item.getExperiencePoints()
                                + ", Value: " + item.getValue() + " Rupees)");
                    } else if (item.getName().equals("Health com.botwAdventureOnline.Items.Potion")) {
                        System.out.println("• " + item.getName() + " (Health points: " + item.getHealthPoints() + ", Value: "
                                + item.getValue() + " Rupees)");
                    } else {
                        System.out.println("• " + item.getName() + " (Damage: " + item.getDamage() + ", Value: "
                                + item.getValue() + " Rupees)");
                    }
                }
                System.out.println();
            }
            if (!koroks.isEmpty()) {
                for (Korok korok : koroks) {
                    System.out.println("• " + korok.getName());
                }
                System.out.println();
            }
            if (hestu != null) {
                System.out.println("• " + hestu.getName());
            }
            if (merchant != null) {
                if (!monsters.isEmpty()) {
                    System.out.println(merchant.getName() + ", the merchant, hides from monsters.");
                    System.out.println("You can trade with " + merchant.getName() + " when there are no monsters on the field");
                } else {
                    System.out.println(merchant.getName() + ", the merchant who greets you.");
                }
            }
        }
    }

    /**
     * Generates a new field with random monsters and loot
     *
     * @return A Field object
     */
    public static Field generateField() {
        Field result;
        ArrayList<Monster> monsters = new ArrayList<>();
        monsters.add(new Bokoblin());
        ArrayList<Item> loot = new ArrayList<>();
        int loop = random.nextInt(3);
        int rand;
        for (int i = 0; i < loop; i++) {
            rand = random.nextInt(2);
            if (rand == 1) {
                monsters.add(new Bokoblin());
            }
            rand = random.nextInt(4);
            if (rand == 1) {
                monsters.add(new Octorok());
            }
            rand = random.nextInt(5);
            if (rand == 1) {
                monsters.add(new Moblin());
            }
            rand = random.nextInt(20);
            if (rand == 1) {
                monsters.add(new Lynel());
            }
        }
        rand = random.nextInt(100);
        if (rand == 1) {
            monsters.add(new Guardian());
        }
        rand = random.nextInt(15);
        if (rand == 1) {
            loot.add(new ExperiencePotion(10));
        } else if (rand == 2) {
            loot.add(new HealthPotion(10));
        }
        rand = random.nextInt(4);
        if (rand == 1) {
            result = new Field(monsters, new Merchant(), loot);
        } else {
            result = new Field(monsters, loot);
        }
        return result;
    }
}
