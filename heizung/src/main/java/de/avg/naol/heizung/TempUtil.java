package de.avg.naol.heizung;

import java.io.Serializable;

class TempUtil implements Serializable {
    private int temperature;

    TempUtil() {
        temperature = (int)(Math.random() * 100 % 10 + 15);
    }

    public int getTemperature() {
        return temperature;
    }
}