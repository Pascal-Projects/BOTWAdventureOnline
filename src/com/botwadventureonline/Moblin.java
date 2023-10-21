package com.botwadventureonline;

import java.util.Random;

public class Moblin extends Monster {
    /**
     * Object to generate random numbers
     */
    private static final Random random = new Random();
    /**
     * Random integer that is used to decide the loot of the Moblin
     */
    private static final int RAND = random.nextInt(3);

    public Moblin() {
        super("Moblin", 200, 20, generateLoot(), RAND * 25);
    }

    /**
     * @return Returns an item which represents the loot of the bokoblin
     */
    private static Item generateLoot() {
        if (RAND == 1) {
            return new HealthPotion(30);
        } else if (RAND == 2) {
            return new Sword("Ancient Sword", 2, 140);
        } else {
            return new HealthPotion(50);
        }
    }
}
