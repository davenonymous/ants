package org.dave.ants.chambers.entrance;

import org.dave.ants.api.chambers.AntChamber;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.properties.calculated.FoodCapacity;
import org.dave.ants.api.properties.calculated.FoodGainPerTick;
import org.dave.ants.api.properties.calculated.MaxAnts;
import org.dave.ants.api.properties.calculated.TotalQueens;
import org.dave.ants.base.BaseNBTSerializable;
import org.dave.ants.hills.HillData;

@AntChamber
public class EntranceChamber extends BaseNBTSerializable implements IAntChamber {

    @Override
    public boolean shouldListInStore() {
        return false;
    }

    @Override
    public String description() {
        return "Beep Boop. You should not be seeing this. Please take a screenshot and report on the issue tracker of this mod. https://github.com/thraaawn/ants/issues";
    }

    @Override
    public void applyHillModification(HillData data) {
        data.modifyPropertyValue(TotalQueens.class, totalQueens -> totalQueens + 1);
        data.modifyPropertyValue(MaxAnts.class, maxAnts -> maxAnts + 10);
        data.modifyPropertyValue(FoodGainPerTick.class, foodGainPerTick -> foodGainPerTick + 0.1d);
        data.modifyPropertyValue(FoodCapacity.class, foodCapacity -> foodCapacity + 10);
    }
}
