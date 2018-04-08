package de.avg.naol.temperatursensor;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        TempSensor tempSensor = new TempSensor();

        while(true) {
            Thread.sleep(2000);
            System.out.println(tempSensor.getTemperature());
        }
    }
}
