package org.dave.ants.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.util.DimPos;

public class ChamberDataMessage implements IMessage {
    public Class<? extends IAntChamber> type;
    public NBTTagCompound chamberData;
    public NBTTagCompound properties;
    public NBTTagCompound maxTierLevels;
    public DimPos pos;

    public ChamberDataMessage() {
    }

    public ChamberDataMessage(Class<? extends IAntChamber> type, NBTTagCompound chamberData, NBTTagCompound properties, NBTTagCompound maxTierLevels, DimPos pos) {
        this.type = type;
        this.chamberData = chamberData;
        this.properties = properties;
        this.maxTierLevels = maxTierLevels;
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = new DimPos(buf.readInt(), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));

        String typeName = ByteBufUtils.readUTF8String(buf);
        try {
            this.type = (Class<? extends IAntChamber>) Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.chamberData = ByteBufUtils.readTag(buf);
        this.properties = ByteBufUtils.readTag(buf);
        this.maxTierLevels = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.dimension);
        buf.writeInt(pos.getBlockPos().getX());
        buf.writeInt(pos.getBlockPos().getY());
        buf.writeInt(pos.getBlockPos().getZ());
        ByteBufUtils.writeUTF8String(buf, type.getName());
        ByteBufUtils.writeTag(buf, chamberData);
        ByteBufUtils.writeTag(buf, properties);
        ByteBufUtils.writeTag(buf, maxTierLevels);
    }
}
