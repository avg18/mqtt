package de.avg.naol.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;

import java.io.Console;
import java.io.IOException;

public class Main {
    private final String LIVINGROOM_HEATING = "livingroom/heating";
    private final String LIVINGROOM_TEMPERATURE = "livingroom/temperature";
    private final String LIVINGROOM_TEMP_TARGET = "livingroom/temperature/target";

    private MqttClient client;
    private ObjectMapper objMap;
    private boolean connected;
    private ClientData clientData;
    private boolean isRunning;
    private Console console;

    public static void main(String[] args) throws MqttException, InterruptedException, IOException {
        new Main();
    }

    private Main() throws MqttException, IOException {
        objMap = new ObjectMapper();
        clientData = new ClientData();
        console = System.console();
        isRunning = true;
        client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());

        try {
            client.connect();
            setSubscriptions();
            connected = true;

        } catch (MqttException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Could not connect, so exiting program.");
            connected = false;
        }

        while(isRunning){
            mainMenuLoop();
        }
    }

    private void mainMenuLoop() throws IOException, MqttException {
        System.out.println("Aktuelle Daten:");
        System.out.println("Temperatursensor, gemessene Temperatur: " + clientData.getTemperature());
        System.out.println("Heizung, Richttemperatur: " + clientData.getTempTarget());
        System.out.println("Heizung ist : " + (clientData.isHeatingOn() ? "AN" : "AUS"));
        System.out.println("Enter: Daten refreshen");
        System.out.println("T: Neue Richttemperatur eingeben:");
        System.out.println("X: Programm beenden");

        console.format(" ");
        String input = console.readLine();

        if("T".equals(input)){
            System.out.println("Bitte Temperatur eingeben: ");
            try {
                input = console.readLine();
                int temp = Integer.parseInt(input);
                publishTempTarget(temp);
                console.format("\n\nNeue gesetzte Temperatur ist: " + input);
                console.format("\n");
            } catch (Exception ex){
                System.out.println("Eingabe leider nicht erkannt\n\n");
            }
        } else if("X".equals(input)){
            isRunning = false;
            client.disconnect();
        }
    }

    private void publishTempTarget(int newTemp) throws JsonProcessingException, MqttException {
        TempUtil tempUtil = new TempUtil(newTemp);
        client.publish(LIVINGROOM_TEMP_TARGET, new MqttMessage(objMap.writeValueAsBytes(tempUtil)));
    }

    private void setSubscriptions() throws MqttException {
        client.setCallback(new MqttCallback() {

            @Override
            public void connectionLost(Throwable throwable) { }

            @Override
            public void messageArrived(String t, MqttMessage m) throws Exception {
                if(LIVINGROOM_TEMP_TARGET.equals(t)){
                    int temp = objMap.readValue(m.getPayload(), TempUtil.class).getTemperature();
                    clientData.setTempTarget(temp);
                } else if(LIVINGROOM_TEMPERATURE.equals(t)){
                    int temp = objMap.readValue(m.getPayload(), TempUtil.class).getTemperature();
                    clientData.setTemperature(temp);
                } else if(LIVINGROOM_HEATING.equals(t)) {
                    boolean isOn = objMap.readValue(m.getPayload(), HeatingStateUtil.class).isHeatingOn();
                    clientData.setHeatingOn(isOn);
                } else {
                    System.out.println("wut");
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken t) { }
        });

        client.subscribe(LIVINGROOM_TEMP_TARGET);
        client.subscribe(LIVINGROOM_TEMPERATURE);
        client.subscribe(LIVINGROOM_HEATING);
    }
}
