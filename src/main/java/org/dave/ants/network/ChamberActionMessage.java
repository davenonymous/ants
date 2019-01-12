package org.dave.ants.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.dave.ants.api.actions.IAntGuiAction;
import org.dave.ants.util.DimPos;

public class ChamberActionMessage implements IMessage {
    DimPos pos;
    Class<? extends IAntGuiAction> actionType;
    NBTTagCompound actionData;

    public ChamberActionMessage() {
    }

    public ChamberActionMessage(DimPos pos, IAntGuiAction action) {
        this.pos = pos;
        this.actionType = action.getClass();
        this.actionData = action.serializeNBT();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = new DimPos(buf.readInt(), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));

        String typeName = ByteBufUtils.readUTF8String(buf);
        try {
            this.actionType = (Class<? extends IAntGuiAction>) Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.actionData = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.dimension);
        buf.writeInt(pos.getBlockPos().getX());
        buf.writeInt(pos.getBlockPos().getY());
        buf.writeInt(pos.getBlockPos().getZ());
        ByteBufUtils.writeUTF8String(buf, actionType.getName());
        ByteBufUtils.writeTag(buf, actionData);
    }
}
