package org.dave.ants.calculation;

import org.dave.ants.api.calculation.IAntCalculation;
import org.dave.ants.hills.HillData;
import org.dave.ants.util.AnnotatedInstanceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculationRegistry {
    private static Map<Integer, List<IAntCalculation>> calculations;

    public static void loadAntCalculations() {
        calculations = new HashMap<>();

        for(IAntCalculation calculation : AnnotatedInstanceUtil.getAntCalculations()) {
            int priority = calculation.priority();
            if(!calculations.containsKey(priority)) {
                calculations.put(priority, new ArrayList<>());
            }

            calculations.get(priority).add(calculation);
        }
    }

    public static void performCalculations(HillData data, long currentWorldTick) {
        calculations.keySet().stream().sorted(Integer::compareTo).map(calculations::get).forEach(actionList -> actionList.forEach(action -> action.calculate(data, currentWorldTick)));
    }
}
