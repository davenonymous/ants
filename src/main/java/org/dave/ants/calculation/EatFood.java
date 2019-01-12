package org.dave.ants.calculation;

import org.dave.ants.api.calculation.AntCalculation;
import org.dave.ants.api.calculation.IAntCalculation;
import org.dave.ants.api.properties.calculated.FoodRequirementPerAnt;
import org.dave.ants.api.properties.stored.StoredFood;
import org.dave.ants.api.properties.stored.TotalAnts;
import org.dave.ants.hills.HillData;

@AntCalculation
public class EatFood implements IAntCalculation {
    @Override
    public int priority() {
        return 2000;
    }

    @Override
    public void calculate(HillData data, long currentWorldTick) {
        double totalAnts = data.getPropertyValue(TotalAnts.class);
        double requirementPerAnt = data.getPropertyValue(FoodRequirementPerAnt.class);

        double requiredFood = totalAnts * requirementPerAnt;

        double storedFood = data.getPropertyValue(StoredFood.class);
        double remainingFood = storedFood - requiredFood;
        data.setPropertyValue(StoredFood.class, Math.max(remainingFood, 0.0d));

        if(remainingFood < 0) {
            data.modifyPropertyValue(TotalAnts.class, ants -> Math.max(ants - 1, 0d));
        }

    }
}
