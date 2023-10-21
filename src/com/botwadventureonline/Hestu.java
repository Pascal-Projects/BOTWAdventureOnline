package com.botwadventureonline;

public class Hestu extends Character {
    /**
     * The amount of Hestu's usages
     */
    private static int hestuUsed = 0;
    /**
     * True if the Hestu has moved to a new location
     */
    private static boolean hestuMoved = false;

    public Hestu() {
        super(1, 0, "Hestu");
    }

    /**
     * @return Returns the amount of Hestu's usages
     */
    public static int getHestuUsed() {
        return hestuUsed;
    }

    /**
     * @param hestuUsed The new amount of Hestu's usages
     */
    public static void setHestuUsed(int hestuUsed) {
        Hestu.hestuUsed = hestuUsed;
    }

    /**
     * @return Returns true if the Hestu has moved to a new location
     */
    public static boolean isHestuMoved() {
        return hestuMoved;
    }

    /**
     * @param hestuMoved The new value for the boolean hestuMoved
     */
    public static void setHestuMoved(boolean hestuMoved) {
        Hestu.hestuMoved = hestuMoved;
    }
}