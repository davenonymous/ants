package org.dave.ants.hills;

import org.dave.ants.api.properties.IHillProperty;
import org.dave.ants.config.GeneralAntHillConfig;
import org.dave.ants.util.AnnotatedInstanceUtil;

import java.util.*;

public class HillPropertyRegistry implements org.dave.ants.api.properties.IHillPropertyRegistry {
    private Map<Class<? extends IHillProperty>, HillPropertyMeta> hillProperties;

    public void findHillProperties() {
        hillProperties = new HashMap<>();

        List<String> disabledHillProperties = Arrays.asList(GeneralAntHillConfig.disabledHillProperties);
        for(Map.Entry<Class<? extends IHillProperty>, Map<String, Object>> hillPropertyEntry : AnnotatedInstanceUtil.getHillPropertyClasses().entrySet()) {
            Class clz = hillPropertyEntry.getKey();
            if(disabledHillProperties.contains(clz.getName())) {
                continue;
            }

            HillPropertyMeta meta = new HillPropertyMeta();
            if(hillPropertyEntry.getValue().containsKey("store")) {
                meta.stored = (boolean)hillPropertyEntry.getValue().get("store");
            } else {
                meta.stored = false;
            }

            hillProperties.put(clz, meta);
        }
    }

    public boolean shouldStoreProperty(Class<? extends IHillProperty> property) {
        return hillProperties.get(property).stored;
    }

    public Set<Class<? extends IHillProperty>> getHillProperties() {
        return hillProperties.keySet();
    }

    @Override
    public void registerHillProperty(Class<? extends IHillProperty> hillPropertyClass, boolean store) {
        if(Arrays.asList(GeneralAntHillConfig.disabledHillProperties).contains(hillPropertyClass.getName())) {
            return;
        }

        HillPropertyMeta meta = new HillPropertyMeta();
        meta.stored = store;

        hillProperties.put(hillPropertyClass, meta);
    }

    private static class HillPropertyMeta {
        public boolean stored;
    }
}
