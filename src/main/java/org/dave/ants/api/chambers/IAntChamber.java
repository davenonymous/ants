package org.dave.ants.api.chambers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import org.dave.ants.api.gui.widgets.WidgetPanel;
import org.dave.ants.hills.HillData;


/**
 * Implement this interface and annotate it with {@link AntChamber} to
 * add a custom chamber type.
 *
 * Please be aware that some of these methods are being called on the
 * client side and some on the server side. Some on both. Make sure you
 * are not calling non-existing methods etc.
 *
 * This must be nbt serializable and should store/load runtime data.
 * This data is a) being synced to the client so you can use its data
 * e.g. when creating the GUI panel. It is b) also written to disk
 * on world save (and loaded on world load).
 *
 */
public interface IAntChamber extends INBTSerializable<NBTTagCompound> {
    /**
     * If this chamber should be buyable in the Hill Store(tm) you need to
     * return true and implement the corresponding methods:
     *   - canBeBought()
     *   - payPrice()
     *   - priceDescription()
     *
     * This value can change over time and is being queried everytime the
     * store is being opened. This means you can limit the visibility of
     * chambers behind some gate mechanism, e.g. science and technology.
     *
     * This is only called on the client side.
     *
     * @return whether or not this chamber type should be listed in the shop
     */
    default boolean shouldListInStore() {
        return true;
    }


    /**
     * Whether or not the hill has enough resources to buy this chamber.
     * Make sure to use the same values as in payPrice()!
     *
     * This is called on the client side and used mainly to enable/disable
     * the buy button for this chamber.
     *
     * @return whether the hill can afford the chamber
     */
    default boolean canBeBought() {
        return false;
    }

    /**
     * Check whether it can be bought again on the live server data, then apply your
     * custom price to the hilldata structure.
     * You probably want to subtract some value from {@link org.dave.ants.api.properties.stored.TotalAnts}.
     * The return value determines whether or not the player should receive the stack,
     * i.e. if the price could not be matched, return false or the player gets a gift.
     *
     * This is called on the server.
     *
     * @param hillData Data structure the Ant hill operates on. Can be modified freely.
     * @return Return false if there were not enough resources to buy this chamber.
     */
    default boolean payPrice(HillData hillData) {
        return false;
    }

    /**
     * This should return a small description of the price this chamber costs.
     * Is only used in the store tooltip at the moment.
     *
     * This is called on the client.
     *
     * @return
     */
    default String priceDescription() {
        return "";
    }

    /**
     * This should return a short, static, localized description for this chamber
     * without involving any runtime data. The chamber might not really exist
     * in any ant hill when this is called, as it is used for item stack tooltips.
     *
     * I repeat: this should be a static description. Ideally translated using
     * {@link net.minecraft.client.resources.I18n#format(String, Object...)}.
     *
     * Only ever called on the client side.
     *
     * @return a static and localized description of this chamber.
     */
    String description();

    /**
     * This method can be used to apply one-time modifications to the hill data.
     * It is called e.g. when the block is placed, but will also get called when
     * a different block is removed or this chamber triggers a stat reset itself.
     *
     * This is called on the server side only and brings the full data structure
     * of the ant hill with it. You can modify this completely, but remember to
     * not do any harm. You should not set a value for example, but in most cases
     * incorporate the previous value!
     *
     * If you want to add additional properties to Ant Hills that are manipulated
     * in your chamber, take a look at the {@link org.dave.ants.api.hill.IHillProperty}
     * interface.
     *
     * @param data Data structure the Ant hill operates on. Can be modified freely.
     */
    void applyHillModification(HillData data);



    /**
     * Return true in this method if you need to do custom stuff each tick.
     * Only use this for world interaction stuff, everything else should get an
     * appropriate "gain" IHillProperty created and its interaction calculated in
     * a custom TODO: IGameTickCalculation.
     *
     * I repeat: If you are dealing only with values pertaining to the ant hill
     * this chamber is in, you do not want to use ticking chambers, but implementations
     * of {@link org.dave.ants.api.hill.IHillProperty} and IGameTickCalculation.
     *
     * Only called on the server.
     *
     * @return whether this chamber should tick.
     */
    default boolean shouldTick() {
        return false;
    }

    /**
     * Do some actions before all IGameTickCalculations have been performed.
     * You usually want to do stuff after the calculations though. Take a look
     * at the tickPostCalc method.
     *
     * Only called on the server.
     *
     * @param data  The hilldata. Consider this read-only at this point.
     *              Messing with it will screw things up for other calculations.
     * @param world The world the chamber is in.
     * @param pos   The position the chamber is at.
     */
    default void tickPreCalc(HillData data, World world, BlockPos pos) {

    }

    /**
     * Do some actions after all IGameTickCalculations have been performed.
     * This is after ants have born and died, after food has been gathered
     * and all the other background calculations that are happening each
     * tick.
     *
     * This is the place to apply appropriate work on neighboring blocks.
     *
     * You could also modify specific hill data properties if those only need
     * to be changed in certain circumstances pertaining the world around
     * this chamber.
     *
     * Only called on the server.
     *
     * @param data  The hilldata.
     * @param world The world the chamber is in.
     * @param pos   The position the chamber is at.
     */
    default void tickPostCalc(HillData data, World world, BlockPos pos) {

    }



    /**
     * Whether or not this chamber has its own GUI tab. If you return true
     * here you have to implement the createGuiPanel() method!
     *
     * Only called on the client.
     *
     * @return whether this chamber has its own GUI
     */
    default boolean hasGui() {
        return false;
    }

    /**
     * Return a {@link WidgetPanel} containing widgets meaningful for your chamber.
     * Upgradeable chambers should show the upgrade level and an upgrade button for
     * example. Read the documentation for the WidgetPanel for further details.
     *
     * You can use the IActionRegistry on the client to fire an IChamberAction
     * that is being executed on the server in the onChamberAction method.
     *
     * Only called on the client.
     *
     * @return A WidgetPanel that is being shown to the client opening your GUI
     */
    default WidgetPanel createGuiPanel() {
        return null;
    }

    /**
     * If you fire an IChamberAction in the WidgetPanel you created in createGuiPanel
     * you can handle here what should happen to this chamber or the whole hill this
     * chamber belongs to. Feel free to modify any data here, but if you do, remember
     * to mark it as dirty and potentially trigger updateHillStatistics on the HillData
     * object.
     *
     * You do not need to send an update packet to the client, this is being done
     * automatically, if necessary.
     *
     * Only called on the server.
     *
     * @param player   The player that performed the action in the GUI.
     * @param action   The actual action that has been performed.
     * @param hillData The runtime data of the hill this chamber belongs to.
     */
    default void onChamberAction(EntityPlayer player, IChamberAction action, HillData hillData) {

    }



    /**
     * If you are using the colored ant item model for your chamber (as specified in
     * your models/item/hill_chamber_<i>lowercased classname</i>.json) you can set this
     * to true and return your desired colors in the ant*ColorTint() methods.
     *
     * This is actually controlling whether or not the IItemColor handler for the
     * chamber hill block is registering a color tint or not.
     *
     * Only called on the client.
     *
     * @return false if you are using your own custom item model.
     */
    default boolean useColoredAntModel() {
        return true;
    }

    /**
     * Return the color the ant should be drawn with.
     * This is the stroke color, i.e. the ants outline.
     *
     * Only called on the client.
     *
     * @return an integer color, e.g. 0xFF0000 for red
     */
    default int antStrokeColorTint() {
        return 0x404040;
    }

    /**
     * Return the color the ant should be drawn with.
     * This is the filling color, i.e. the ants inside.
     *
     * Only called on the client.
     *
     * @return an integer color, e.g. 0x00FF00 for green
     */
    default int antFillingColorTint() {
        return 0xFFFF00;
    }

    /**
     * Return the color the ant should be drawn with.
     * This is the color of the wings.
     *
     * Only called on the client.
     *
     * @return an integer color, e.g. 0x0000FF for blue
     */
    default int antWingsColorTint() {
        return 0x808080;
    }

}