package org.dave.ants.calculation;

import org.dave.ants.api.calculation.AntCalculation;
import org.dave.ants.api.calculation.IAntCalculation;
import org.dave.ants.api.properties.calculated.AntsBornPerHatching;
import org.dave.ants.api.properties.calculated.FoodRequirementPerAnt;
import org.dave.ants.api.properties.calculated.MaxAnts;
import org.dave.ants.api.properties.calculated.TicksBetweenBabies;
import org.dave.ants.api.properties.stored.LastAntBornTick;
import org.dave.ants.api.properties.stored.StoredFood;
import org.dave.ants.api.properties.stored.UsableAnts;
import org.dave.ants.hills.HillData;

@AntCalculation
public class CreateNewAnts implements IAntCalculation {
    @Override
    public int priority() {
        return 1000;
    }

    @Override
    public void calculate(HillData data, long currentWorldTick) {
        double usableAnts = data.getPropertyValue(UsableAnts.class);
        double maxAnts = data.getPropertyValue(MaxAnts.class);

        // Don't bear new babies if there is no more room
        if(usableAnts >= maxAnts) {
            return;
        }

        // Check if its time already
        if(data.getPropertyValue(LastAntBornTick.class) + data.getPropertyValue(TicksBetweenBabies.class) >= currentWorldTick) {
            return;
        }

        double requiredFood = 1000 * data.getPropertyValue(FoodRequirementPerAnt.class);
        if(data.getPropertyValue(StoredFood.class) < requiredFood) {
            return;
        }

        // Create ants
        double antsBornPerHatching = data.getPropertyValue(AntsBornPerHatching.class);

        data.setPropertyValue(UsableAnts.class, Math.min(usableAnts + antsBornPerHatching, maxAnts));

        // Remember when we last created ants
        data.setPropertyValue(LastAntBornTick.class, currentWorldTick);

        // It costs food to create new ants
        data.modifyPropertyValue(StoredFood.class, storedFood -> storedFood - (storedFood * 0.1d));
    }
}
