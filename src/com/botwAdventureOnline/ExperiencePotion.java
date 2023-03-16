package com.botwAdventureOnline;


public class ExperiencePotion extends Potion {

    /**
     * @param pExperiencePoints Sets the experience points of the potion which the
     *                          player gets after drinking it
     */
    public ExperiencePotion(int pExperiencePoints) {
        super("Experience Potion",
                pExperiencePoints * 2,
                0,
                pExperiencePoints);
    }
}