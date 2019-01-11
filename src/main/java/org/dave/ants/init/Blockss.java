package org.dave.ants.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dave.ants.Ants;
import org.dave.ants.blocks.ChamberHillBlock;
import org.dave.ants.blocks.EntranceHillBlock;

@GameRegistry.ObjectHolder(Ants.MODID)
public class Blockss {
    @GameRegistry.ObjectHolder("hill_entrance")
    public static EntranceHillBlock hillEntrance;

    @GameRegistry.ObjectHolder("hill_chamber")
    public static ChamberHillBlock chamber;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        hillEntrance.initModel();
        chamber.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
        hillEntrance.initItemModel();
        chamber.initItemModel();
    }
}
