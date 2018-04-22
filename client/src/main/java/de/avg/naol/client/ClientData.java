package de.avg.naol.client;

public class ClientData {
    private int temperature;
    private int tempTarget;
    private boolean isHeatingOn;

    public ClientData() {
        this.temperature = 19;
        this.tempTarget = 20;
        this.isHeatingOn = true;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getTempTarget() {
        return tempTarget;
    }

    public void setTempTarget(int tempTarget) {
        this.tempTarget = tempTarget;
    }

    public boolean isHeatingOn() {
        return isHeatingOn;
    }

    public void setHeatingOn(boolean heatingOn) {
        this.isHeatingOn = heatingOn;
    }
}
