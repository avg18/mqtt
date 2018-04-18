package de.avg.naol.heizung;

public class Heating {
    private int temperature;
    private int tempTarget;

    public Heating() {
        temperature = 19;
        tempTarget = 20;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setTempTarget(int tempTarget) {
        this.tempTarget = tempTarget;
    }

    public boolean isOn(){
        return tempTarget > temperature;
    }

}
