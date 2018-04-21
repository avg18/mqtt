package de.avg.naol.heizung;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;

public class Main {
    private final String LIVINGROOM_HEATING = "livingroom/heating";
    private final String LIVINGROOM_TEMPERATURE = "livingroom/temperature";
    private final String LIVINGROOM_TEMP_TARGET = "livingroom/temperature/target";
    private final String MOSQUITTO_URL = "tcp://localhost:1883";

    private MqttClient client;
    private ObjectMapper objMap;
    private Heating heating;
    private boolean connected;

    public static void main(String[] args) throws MqttException, InterruptedException, JsonProcessingException {
        new Main();
    }

    private Main() throws MqttException, InterruptedException, JsonProcessingException {
        objMap = new ObjectMapper();
        client = new MqttClient(MOSQUITTO_URL, MqttClient.generateClientId());
        heating = new Heating();

        try {
            client.connect();
            setSubscriptions();
            connected = true;
        } catch (MqttException ex) {
            System.out.println(ex.getMessage() + "");
            connected = false;
        }

        for(int i=0; i<999 && connected; i++) { // Publish data i-many times
//            publishHeatingState();
            Thread.sleep(3000); // Publish new data every 10 seconds
        }

        client.disconnect();
    }

    private void publishHeatingState() throws MqttException, JsonProcessingException {
        HeatingStateUtil isOn = new HeatingStateUtil(heating.isOn());

        System.out.println(LIVINGROOM_HEATING + ": " + objMap.writeValueAsString(isOn));
        client.publish(LIVINGROOM_HEATING, new MqttMessage(objMap.writeValueAsBytes(isOn)));
    }

    private void setSubscriptions() throws MqttException {
        client.setCallback(new MqttCallback() {

            @Override
            public void connectionLost(Throwable throwable) { }

            @Override
            public void messageArrived(String t, MqttMessage m) throws Exception {
                System.out.println(t + ": " + new String(m.getPayload()));
                    int temp = objMap.readValue(m.getPayload(), TempUtil.class).getTemperature();

                if(LIVINGROOM_TEMP_TARGET.equals(t)){
                    heating.setTempTarget(temp);
                    publishHeatingState();
                } else if(LIVINGROOM_TEMPERATURE.equals(t)){
                    heating.setTemperature(temp);
                    publishHeatingState();
                } else {
                    System.out.println("This shouldn't happen! Bad, Mosquitto, bad!");
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken t) { }
        });

        client.subscribe(LIVINGROOM_TEMP_TARGET);
        client.subscribe(LIVINGROOM_TEMPERATURE);
    }
}
