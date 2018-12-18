import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class ServerCallback implements MqttCallback {
    private MainServer server;

    public ServerCallback(MainServer server) {
        this.server = server;
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String message = new String(mqttMessage.getPayload());
        System.out.println("Message Arrived:" + message);
        switch (topic) {
            case "trashcanDiscoveryResponse":
                handleTrashcanDiscoveryResponse(message);
                break;
            case "garbagetruckDiscoveryResponse":
                handleGarbagetruckDiscoveryResponse(message);
                break;
            default:
                if (message.equals("getHistoryEntry")) {
                    break;
                }
                JSONObject jsonMessage = new JSONObject(message);
                if (jsonMessage.getString("action").equals("getHistoryEntryResponse")) {
                    TrashcanHistoryEntry trashcanHistoryEntry = new TrashcanHistoryEntry(jsonMessage.getJSONObject("data"));
                    TrashcanHistory trashcanHistory = server.getTrashcanHistoryById(topic);
                    trashcanHistory.addEntry(trashcanHistoryEntry);
                    System.out.println(message);
                }
                break;
        }
        System.out.println("Message received:\n\t" + message);
    }

    private void handleTrashcanDiscoveryResponse(String message) {
        System.out.println(message);
        try {
            JSONObject trashcanJson = new JSONObject(message);
            Location location = new Location(
                    trashcanJson.getJSONObject("location").getDouble("longitude"),
                    trashcanJson.getJSONObject("location").getDouble("latitude")
            );
            String trashCanId = trashcanJson.getString("trashcanId");
            TrashcanHistory trashcanHistory = new TrashcanHistory(trashCanId, location);
            if (!server.trashcanHistories.contains(trashcanHistory)) {
                server.trashcanHistories.add(trashcanHistory);
                server.mqttClient.subscribe(trashCanId);
            }
            server.mqttClient.sendMessage(trashCanId, "getHistoryEntry");
        } catch (JSONException ex) {
            System.out.println("Message:" + ex.getMessage());
        }
    }

    private void handleGarbagetruckDiscoveryResponse(String message) {
        System.out.println(message);
        try {
            JSONObject garbagetruckJson = new JSONObject(message);
            String garbagetruckId = garbagetruckJson.getString("garbageTruckId");
            if (!server.garbageTruckIds.contains(garbagetruckId)) {
                server.garbageTruckIds.add(garbagetruckId);
                server.mqttClient.subscribe(garbagetruckId);
            }
        } catch (JSONException ex) {
            System.out.println("Message:" + ex.getMessage());
        }
    }

    private void switchTrashcan(String message, Trashcan trashcan) {

    }

    private void switchGarbageTruck(String message) {
        switch (message) {

        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
