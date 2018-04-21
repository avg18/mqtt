package de.avg.naol.temperatursensor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;

public class Main {
    private MqttClient client;
    private ObjectMapper objMap;
    private boolean connected;
    private final String MOSQUITTO_URL = "tcp://localhost:1883";


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
            System.out.println(ex.getMessage() + "Could not connect to MQTT Server!");
            connected = false;
        }

        for(int i=0; i<999 && connected; i++) { // Publish data i-many times
            publishTemperature();
            Thread.sleep(3000); // Publish new data every 3 seconds
        }

        client.disconnect();
    }

    private void publishTemperature() throws MqttException, JsonProcessingException {
        TempUtil payload = new TempUtil();

        client.publish("livingroom/temperature", new MqttMessage((objMap.writeValueAsBytes(payload))));
        System.out.println("livingroom/temperature: " + objMap.writeValueAsString(payload));
    }
}
