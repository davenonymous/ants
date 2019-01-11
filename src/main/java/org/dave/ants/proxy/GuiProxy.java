package org.dave.ants.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import org.dave.ants.gui.AntHillContainer;
import org.dave.ants.gui.AntHillGuiContainer;

import javax.annotation.Nullable;

public class GuiProxy implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == GuiIDS.ANT_HILL.ordinal()) {
            return new AntHillContainer(world, new BlockPos(x, y, z));
        }

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == GuiIDS.ANT_HILL.ordinal()) {
            return new AntHillGuiContainer(new AntHillContainer(world, new BlockPos(x, y, z)));
        }

        return null;
    }

    public enum GuiIDS {
        ANT_HILL
    }
}
