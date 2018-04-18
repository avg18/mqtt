package de.avg.naol.heizung;

public class HeatingStateUtil {
    private boolean isOn;

    public HeatingStateUtil(boolean isOn) {
        this.isOn = isOn;
    }

    public boolean isHeatingOn() {
        return isOn;
    }
}
