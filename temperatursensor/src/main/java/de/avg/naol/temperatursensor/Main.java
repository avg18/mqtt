package de.avg.naol.temperatursensor;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Main {

    public static void main(String[] args) throws InterruptedException, MqttException {
        TempSensor tempSensor = new TempSensor();
        MqttClient client = new MqttClient("tcp://localhost:1000", "temperatursensor");

        while (true) {
            Thread.sleep(3000);
            client.publish("/livingroom/temperature", new MqttMessage(tempSensor.getTemperature().getBytes()));
        }
    }
}
