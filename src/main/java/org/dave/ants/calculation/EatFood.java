package org.dave.ants.calculation;

import org.dave.ants.api.calculation.AntCalculation;
import org.dave.ants.api.calculation.IAntCalculation;
import org.dave.ants.api.properties.calculated.FoodRequirementPerAnt;
import org.dave.ants.api.properties.stored.StoredFood;
import org.dave.ants.api.properties.stored.UsableAnts;
import org.dave.ants.hills.HillData;

@AntCalculation
public class EatFood implements IAntCalculation {
    @Override
    public int priority() {
        return 2000;
    }

    @Override
    public void calculate(HillData data, long currentWorldTick) {
        double usableAnts = data.getPropertyValue(UsableAnts.class);
        double requirementPerAnt = data.getPropertyValue(FoodRequirementPerAnt.class);

        double requiredFood = usableAnts * requirementPerAnt;

        double storedFood = data.getPropertyValue(StoredFood.class);
        double remainingFood = storedFood - requiredFood;
        data.setPropertyValue(StoredFood.class, Math.max(remainingFood, 0.0d));

        if(remainingFood < 0) {
            data.modifyPropertyValue(UsableAnts.class, ants -> Math.max(ants - 1, 0d));
        }

    }
}
