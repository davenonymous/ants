package org.dave.ants.misc;


import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.dave.ants.hills.HillRegistry;

public class WorldSavedDataRegistry {
    public HillRegistry hills;

    @SubscribeEvent
    public void loadWorld(WorldEvent.Load event) {
        if(event.getWorld().isRemote || event.getWorld().provider.getDimension() != 0) {
            return;
        }

        hills = (HillRegistry) event.getWorld().getMapStorage().getOrLoadData(HillRegistry.class, "AntHillRegistry");
        if(hills == null) {
            hills = new HillRegistry();
            hills.markDirty();
            event.getWorld().getMapStorage().setData("AntHillRegistry", hills);
        }
        hills.afterLoad();
    }
}
