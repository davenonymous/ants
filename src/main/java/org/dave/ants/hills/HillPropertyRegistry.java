package org.dave.ants.hills;

import org.dave.ants.api.hill.IHillProperty;
import org.dave.ants.config.GeneralAntHillConfig;
import org.dave.ants.util.AnnotatedInstanceUtil;

import java.util.*;

public class HillPropertyRegistry {
    private static Map<Class<? extends IHillProperty>, HillPropertyMeta> hillProperties;

    public static void findHillProperties() {
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

    public static boolean shouldStoreProperty(Class<? extends IHillProperty> property) {
        return hillProperties.get(property).stored;
    }

    public static Set<Class<? extends IHillProperty>> getHillProperties() {
        return hillProperties.keySet();
    }


    private static class HillPropertyMeta {
        public boolean stored;
    }
}
