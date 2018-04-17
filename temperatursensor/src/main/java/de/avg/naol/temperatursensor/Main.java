package de.avg.naol.temperatursensor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;

public class Main {
    private MqttClient client;
    private ObjectMapper objMap;
    boolean connected;

    public static void main(String[] args) throws MqttException, InterruptedException, JsonProcessingException {
        new Main();
    }

    private Main() throws MqttException, InterruptedException, JsonProcessingException {
        objMap = new ObjectMapper();
        client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());

        try {
            client.connect();
            connected = true;

        } catch (MqttException ex) {
            System.out.println(ex.getMessage() + "");
            connected = false;
        }

        for(int i=0; i<21 && connected; i++) { // Publish data 20 times
            publishTemperature();
            Thread.sleep(3000); // Publish new data every 3 seconds
        }
    }

    private void publishTemperature() throws MqttException, JsonProcessingException {
        TempUtil payload = new TempUtil();

        client.publish("livingroom/temperature", new MqttMessage((objMap.writeValueAsBytes(payload))));
        System.out.println(objMap.writeValueAsString(payload));
    }
}
