package com.botwAdventureOnline;

import java.util.Random;

public class Octorok extends Monster {

    /**
     * Object to generate random numbers
     */
    private static final Random random = new Random();

    /**
     * Random integer that is used to decide the loot of the bokoblin
     */
    private static final int rand = random.nextInt(4);

    /**
     * @return Returns an item which represents the loot of the Octorok
     */
    private static Item generateLoot() {
        if (rand == 1) {
            return new ExperiencePotion(100);
        } else if (rand == 2) {
            return new Sword("Knight's Broadsword", 2, 126);
        } else if (rand == 3) {
            return new Sword("Soldier's Broadsword", 2, 114);
        } else {
            return new HealthPotion(100);
        }
    }

    public Octorok() {
        super("Octorok", 50, 20, generateLoot(), rand * 20);
    }
}
