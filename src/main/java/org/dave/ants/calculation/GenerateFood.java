package org.dave.ants.calculation;

import org.dave.ants.api.calculation.AntCalculation;
import org.dave.ants.api.calculation.IAntCalculation;
import org.dave.ants.api.properties.calculated.FoodCapacity;
import org.dave.ants.api.properties.calculated.FoodGainPerTick;
import org.dave.ants.api.properties.stored.StoredFood;
import org.dave.ants.hills.HillData;

@AntCalculation
public class GenerateFood implements IAntCalculation {
    @Override
    public int priority() {
        return 0;
    }

    @Override
    public void calculate(HillData data, long currentWorldTick) {
        double storedFood = data.getPropertyValue(StoredFood.class);
        double foodGainPerTick = data.getPropertyValue(FoodGainPerTick.class);
        double maxCapacity = data.getPropertyValue(FoodCapacity.class);

        data.setPropertyValue(StoredFood.class, Math.min(storedFood + foodGainPerTick, maxCapacity));
    }
}
