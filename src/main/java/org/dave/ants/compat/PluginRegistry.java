package org.dave.ants.compat;

import org.dave.ants.api.IAntPlugin;
import org.dave.ants.config.GeneralAntHillConfig;
import org.dave.ants.util.AnnotatedInstanceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class PluginRegistry {
    private List<IAntPlugin> plugins;

    public void loadAntsPlugins() {
        plugins = new ArrayList<>();

        List<String> disabledPlugins = Arrays.asList(GeneralAntHillConfig.disabledPlugins);
        for(IAntPlugin plugin : AnnotatedInstanceUtil.getAntPlugins()) {
            if(disabledPlugins.contains(plugin.getClass().getName())) {
                continue;
            }

            plugins.add(plugin);
        }
    }

    public void forEach(Consumer<IAntPlugin> pluginConsumer) {
        plugins.forEach(pluginConsumer);
    }
}
