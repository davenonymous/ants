package org.dave.ants.actions;

public class BuyAnt extends BaseAction {
    public static double ticksPerClick = 20.0d;
    public static double totalRange = 100.0d;

    public static int[] colors = new int[] { 0xffc2d655, 0xfff3e066, 0xffde8b56, 0xff932828 };
    public static double[] price = new double[] { 0.5d, 1.0d, 2.0d, 5.0d };
    public static double[] gain = new double[] { 1.0, 0.5, 0.25, 0.05 };

    public static int getIndex(double cooldown) {
        int index = (int)Math.floor(cooldown / (BuyAnt.totalRange / BuyAnt.colors.length));
        return Math.min(BuyAnt.colors.length-1, Math.max(0, index));
    }
}
