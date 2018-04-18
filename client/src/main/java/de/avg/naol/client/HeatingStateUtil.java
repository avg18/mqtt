package de.avg.naol.client;

public class HeatingStateUtil {
    private boolean heatingOn;

    public HeatingStateUtil(boolean isOn) {
        this.heatingOn = isOn;
    }

    public HeatingStateUtil() {
    }

    public boolean isHeatingOn() {
        return heatingOn;
    }

    public void setHeatingOn(boolean heatingOn) {
        this.heatingOn = heatingOn;
    }
}
