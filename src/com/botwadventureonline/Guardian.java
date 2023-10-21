package com.botwadventureonline;

import java.util.Random;

public class Guardian extends Monster {
    private static Random random = new Random();

    /**
     * This Returns a Potion which is dropped by the Guardian when it gets killed
     *
     * @return Potion
     */
    private static Item generateLoot() {
        int rand = random.nextInt(2);
        if (rand == 1) {
            return new HealthPotion(300);
        } else {
            return new ExperiencePotion(100);
        }
    }

    public Guardian() {
        super("Guardian", 505, 40, generateLoot(), 350);
    }


}
