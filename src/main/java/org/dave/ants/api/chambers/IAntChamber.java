package org.dave.ants.api.chambers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import org.dave.ants.api.actions.IAntGuiAction;
import org.dave.ants.api.gui.widgets.WidgetPanel;
import org.dave.ants.api.properties.IHillProperty;
import org.dave.ants.hills.HillData;

import java.util.Collections;
import java.util.List;


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
     * Return a list of tiers this chamber can be upgraded too.
     * You might want to follow the official blocks or change it up
     * completely.
     *
     * If you return an empty list, no tier upgrades will be possible
     * and Dirt will be set as default IBlockState for the first tier.
     *
     * @return a list containing the blockstates representing the tier
     *         of the chamber at the given level/list index.
     */
    default List<IBlockState> getTierList() {
        return Collections.emptyList();
    }


    /**
     * Return the price a specific tier upgrades costs (in ants).
     * It is guaranteed for the tier to be a valid tier, otherwise this method
     * will not get called in the first place.
     *
     * @param tier  The tier the chamber is currently at
     * @param state And the blockstate representing that tier
     * @return The price to upgrade to this tier/to buy the first tier
     */
    default double tierCost(int tier, IBlockState state) {
        return 15.0d;
    }

    /**
     * If this chamber should be buyable in the Hill Store(tm) you need to
     * return true here.
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
     * in your chamber, take a look at the {@link IHillProperty}
     * interface.
     *
     * @param data Data structure the Ant hill operates on. Can be modified freely.
     * @param chamberTier
     */
    void applyHillModification(HillData data, int chamberTier);



    /**
     * Return true in this method if you need to do custom stuff each tick.
     * Only use this for world interaction stuff, everything else should get an
     * appropriate "gain" IHillProperty created and its interaction calculated in
     * a custom {@link org.dave.ants.api.calculation.IAntCalculation}.
     *
     * I repeat: If you are dealing only with values pertaining to the ant hill
     * this chamber is in, you do not want to use ticking chambers, but implementations
     * of {@link IHillProperty} and IGameTickCalculation.
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
     * You can use the IActionRegistry on the client to fire an IAntGuiAction
     * that is being executed on the server in the onChamberAction method.
     *
     * Only called on the client.
     *
     * @param tier The current tier this chamber is at.
     *
     * @return A WidgetPanel that is being shown to the client opening your GUI
     */
    default WidgetPanel createGuiPanel(int tier) {
        return null;
    }

    /**
     * If you fire an IAntGuiAction in the WidgetPanel you created in createGuiPanel
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
    default void onChamberAction(EntityPlayer player, IAntGuiAction action, HillData hillData) {

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
