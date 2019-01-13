package org.dave.ants.config;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import java.util.Arrays;
import java.util.List;

public class GeneralAntHillConfig {
    public static final String CONFIG_CATEGORY = "General Ant Hill Config";

    public static String[] disabledChambers = new String[] {};
    public static String[] disabledHillProperties = new String[] {};
    public static String[] disabledPlugins = new String[] {};

    public static List<IBlockState> defaultTierList = Arrays.asList(
            Blocks.DIRT.getDefaultState(),
            Blocks.CLAY.getDefaultState(),
            Blocks.COAL_BLOCK.getDefaultState(),
            Blocks.IRON_BLOCK.getDefaultState(),
            Blocks.QUARTZ_BLOCK.getDefaultState(),
            Blocks.LAPIS_BLOCK.getDefaultState(),
            Blocks.GOLD_BLOCK.getDefaultState(),
            Blocks.REDSTONE_BLOCK.getDefaultState(),
            Blocks.DIAMOND_BLOCK.getDefaultState(),
            Blocks.OBSIDIAN.getDefaultState(),
            Blocks.EMERALD_BLOCK.getDefaultState()
    );

    public static List<Double> defaultTierCost = Arrays.asList(
            15d,
            200d,
            1000d,
            5000d,
            20000d,
            80000d,
            400000d,
            1_500_000d,
            100_000_000d,
            4_000_000_000d,
            75_000_000_000d
    );

    public static List<Double> defaultTierIncomeRate = Arrays.asList(
            0.1d,
            0.5d,
            4d,
            10d,
            40d,
            100d,
            400d,
            5000d,
            100_000d,
            1_000_000d,
            10_000_000d
    );

    public static double defaultUpgradeMultiplier = 1.10d;
    public static double defaultWorkerMultiplier = 1.05d;
}
