package com.botwAdventureOnline;

import java.util.Random;


public class Bokoblin extends Monster {

    /**
     * Object to generate random numbers
     */
    private static final Random random = new Random();

    /**
     * Random integer that is used to decide the loot of the Bokoblin
     */
    private static final int rand = random.nextInt(3);

    public Bokoblin() {
        super("Bokoblin", 100, 10, addLoot(), rand * 10);
    }

    /**
     * @return Returns an item which represents the loot of the bokoblin
     */
    private static Item addLoot() {
        if (rand == 1) {
            return new HealthPotion(30);
        } else if (rand == 2) {
            return new Sword("Boko Bat", 1, 105);
        } else {
            return new HealthPotion(20);
        }
    }
}
