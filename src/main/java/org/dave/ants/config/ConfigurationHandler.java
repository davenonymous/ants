package org.dave.ants.config;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.dave.ants.Ants;
import org.dave.ants.api.properties.calculated.FoodRequirementPerAnt;
import org.dave.ants.api.properties.calculated.TicksBetweenBabies;
import org.dave.ants.chambers.foraging.ForagingGrounds;
import org.dave.ants.chambers.livingquarters.LivingQuarters;
import org.dave.ants.chambers.queen.QueensChamber;
import org.dave.ants.chambers.storage.StorageChamber;
import org.dave.ants.util.Logz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationHandler {
    public static Configuration configuration;
    public static File baseDirectory;

    public static void init(File configFile) {
        if(configuration != null) {
            return;
        }

        baseDirectory = new File(configFile.getParentFile(), "ants");
        if(!baseDirectory.exists()) {
            baseDirectory.mkdir();
        }

        configuration = new Configuration(new File(baseDirectory, "settings.cfg"), null);
        loadConfiguration();
    }

    private static void loadConfiguration() {
        Logz.info("Loading configuration");

        TicksBetweenBabies.clampTicksBetweenBabies = configuration.getInt("Minimum ticks between babies", GeneralAntHillConfig.CONFIG_CATEGORY, 20, 1, Integer.MAX_VALUE, "No upgrades can reduce the hatchling frequency below this value.");
        TicksBetweenBabies.baseTicksBetweenBabies = configuration.getInt("Base ticks between babies", GeneralAntHillConfig.CONFIG_CATEGORY, 20*5, 1, Integer.MAX_VALUE, "How many ticks between hatchlings without any upgrades");

        //FoodRequirementPerAnt.baseFoodRequirementPerAnt = configuration.getFloat("Food requirement per ant", GeneralAntHillConfig.CONFIG_CATEGORY, 0.005f, 0, Float.MAX_VALUE, "How much food a single ant eats per tick");

        //ForagingGrounds.priceToBuy = configuration.getFloat("Cost", ForagingGrounds.CONFIG_CATEGORY, 20.0f, 0.0f, Float.MAX_VALUE, "Price to buy (in ants)");
        //ForagingGrounds.baseWorkerPrice = configuration.getFloat("Worker cost", ForagingGrounds.CONFIG_CATEGORY, 20.0f, 0.0f, Float.MAX_VALUE, "Price to hire another worker (in ants)");
        //ForagingGrounds.workersPerUpgrade = configuration.getInt("Additional workers per upgrade", ForagingGrounds.CONFIG_CATEGORY, 5, 0, Integer.MAX_VALUE, "How many additional worker slots are unlocked per Upgrade.");
        //ForagingGrounds.upgradeProductionIncreaseMultiplier = configuration.getFloat("Production increase per worker", ForagingGrounds.CONFIG_CATEGORY, 0.05f, 0.0f, Float.MAX_VALUE, "How much food each worker can gather by default.");

        //LivingQuarters.priceToBuy = configuration.getFloat("Cost", LivingQuarters.CONFIG_CATEGORY, 10.0f, 0.0f, Float.MAX_VALUE, "Price to buy (in ants)");

        //QueensChamber.priceToBuy = configuration.getFloat("Cost", QueensChamber.CONFIG_CATEGORY, 250.0f, 0.0f, Float.MAX_VALUE, "Price to buy (in ants)");
        //QueensChamber.minimumBedsToBeVisible = configuration.getFloat("Minimum beds to be visible", QueensChamber.CONFIG_CATEGORY, 200.0f, 0.0f, Float.MAX_VALUE, "Only show the queens chamber with this many beds in the hill.");
        //QueensChamber.ticksReducedPerWorker = configuration.getInt("Ticks reduced per worker", QueensChamber.CONFIG_CATEGORY, 1, 0, Integer.MAX_VALUE, "How many ticks each worker reduces the hatchling frequency");
        //QueensChamber.priceForWorkers = configuration.getFloat("Worker cost", QueensChamber.CONFIG_CATEGORY, 100.0f, 0.0f, Float.MAX_VALUE, "Price to hire another worker (in ants)");
        //QueensChamber.maxWorkers = configuration.getInt("Worker slots", QueensChamber.CONFIG_CATEGORY, 5, 0, Integer.MAX_VALUE, "How many workers can be hired per chamber");

        //StorageChamber.priceToBuy = configuration.getFloat("Cost", StorageChamber.CONFIG_CATEGORY, 10.0f, 0.0f, Float.MAX_VALUE, "Price to buy (in ants)");

        GeneralAntHillConfig.disabledChambers = configuration.getStringList("Chambers to disable", GeneralAntHillConfig.CONFIG_CATEGORY, new String[] {}, "Must be specified by classname, e.g. org.dave.ants.chambers.queen.QueensChamber)");
        GeneralAntHillConfig.disabledHillProperties = configuration.getStringList("Properties to disable", GeneralAntHillConfig.CONFIG_CATEGORY, new String[] {}, "Risky to change! Must be specified by classname, e.g. org.dave.ants.api.properties.calculated.AntsBornPerHatching)");

        if(configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static List<IConfigElement> getConfigElements() {
        List<IConfigElement> result = new ArrayList<>();
        result.add(new ConfigElement(configuration.getCategory(GeneralAntHillConfig.CONFIG_CATEGORY)));
        result.add(new ConfigElement(configuration.getCategory(ForagingGrounds.CONFIG_CATEGORY)));
        result.add(new ConfigElement(configuration.getCategory(LivingQuarters.CONFIG_CATEGORY)));
        result.add(new ConfigElement(configuration.getCategory(QueensChamber.CONFIG_CATEGORY)));
        result.add(new ConfigElement(configuration.getCategory(StorageChamber.CONFIG_CATEGORY)));

        return result;
    }

    public static void saveConfiguration() {
        Logz.info("Saving configuration");
        configuration.save();
    }

    @SubscribeEvent
    public static void onConfigurationChanged(ConfigChangedEvent event) {
        if(!event.getModID().equalsIgnoreCase(Ants.MODID)) {
            return;
        }

        loadConfiguration();
    }
}
