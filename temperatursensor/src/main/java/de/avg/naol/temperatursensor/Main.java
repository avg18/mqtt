package de.avg.naol.temperatursensor;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Main {

    public static void main(String[] args) throws InterruptedException, MqttException {
        TempSensor tempSensor = new TempSensor();
        MqttClient client = new MqttClient("tcp://localhost:1000", MqttClient.generateClientId());
        client.connect();

        while (true) {
            String payload = tempSensor.getTemperature();

            client.publish("livingroom/temperature", new MqttMessage(payload.getBytes()));
            System.out.println(payload);

            Thread.sleep(3000);
        }
    }
}
