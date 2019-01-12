package org.dave.ants.calculation;

import org.dave.ants.api.calculation.IAntCalculation;
import org.dave.ants.api.calculation.ICalculationRegistry;
import org.dave.ants.hills.HillData;
import org.dave.ants.util.AnnotatedInstanceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculationRegistry implements ICalculationRegistry {
    private Map<Integer, List<IAntCalculation>> calculations;

    public void loadAntCalculations() {
        calculations = new HashMap<>();

        for(IAntCalculation calculation : AnnotatedInstanceUtil.getAntCalculations()) {
            registerCalculation(calculation);
        }
    }

    public void performCalculations(HillData data, long currentWorldTick) {
        calculations.keySet().stream().sorted(Integer::compareTo).map(calculations::get).forEach(actionList -> actionList.forEach(action -> action.calculate(data, currentWorldTick)));
    }

    @Override
    public void registerCalculation(IAntCalculation calculation) {
        int priority = calculation.priority();
        if(!calculations.containsKey(priority)) {
            calculations.put(priority, new ArrayList<>());
        }

        calculations.get(priority).add(calculation);
    }
}
