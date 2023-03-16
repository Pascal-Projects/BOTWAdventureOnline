package com.botwAdventureOnline;

import java.util.Random;


public class Lynel extends Monster {
    /**
     * Object to generate random numbers
     */
    private static final Random random = new Random();
    /**
     * Randomly generated number which defines the ep and the loot of the Lynel
     */
    private static final int rand = random.nextInt(2);

    public Lynel() {
        super("Lynel", 50, 20, generateLoot(), rand * 100);
    }

    /**
     * @return Returns the loot of the Lynel
     */
    private static Item generateLoot() {
        if (rand == 1) {
            return new HealthPotion(200);
        } else {
            return new Sword("Lynel Sword", 3, 124);
        }
    }
}