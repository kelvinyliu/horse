package model;

import java.util.ArrayList;

public class HorseSetupModel {
    public final ArrayList<Horse> horses = new ArrayList<>();

    public int getHorseCount() {
        return horses.size();
    }

    public void addHorse(Horse horse) {
        horses.add(horse);
    }

    public void removeHorse(int index) {
        horses.remove(index);
    }
}
