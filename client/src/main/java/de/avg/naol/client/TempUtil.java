package de.avg.naol.client;

import java.io.Serializable;

class TempUtil implements Serializable {
    private int temperature;

    TempUtil() {
        temperature = (int)(Math.random() * 100 % 10 + 15);
    }

    public TempUtil(int temperature) {
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }
}