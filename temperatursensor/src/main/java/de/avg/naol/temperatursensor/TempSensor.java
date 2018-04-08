package de.avg.naol.temperatursensor;

class TempSensor {

    TempSensor() {
    }

    String getTemperature() {
        return ((Integer)((int)(Math.random() * 100 % 10 + 15))).toString();
    }
}
