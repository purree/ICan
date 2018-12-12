import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class GarbageTruckCallback implements MqttCallback {
    private GarbageTruck garbageTruck;

    public GarbageTruckCallback(GarbageTruck garbageTruck) {
        this.garbageTruck = garbageTruck;
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String message = new String(mqttMessage.getPayload());
        System.out.println("Message received:\n\t" + message);
        takeAction(topic, message);

    }

    private void takeAction(String topic, String message) {
        switch (topic) {
            case "trashcan1":
                switchTrashcan1(message);
                break;

            case "trashcan2" :
                switchTrashcan1(message);
                break;

            case "trashcan3" :
                switchTrashcan1(message);
                break;

            case "server" :
                switchServer(message);
                break;

        }
    }

    private void switchTrashcan1(String message){
        switch(message.split(":")[0]){
            case "temperature":
                break;
            case "trashlevel" :
                break;
        }
    }

    private void switchTrashcan2(String message){
        switch(message.split(":")[0]){
            case "temperature":
                break;
            case "trashlevel" :
                break;
        }
    }

    private void switchTrashcan3(String message){
        switch(message.split(":")[0]){
            case "temperature":
                break;
            case "trashlevel" :
                break;
        }
    }

    private void switchServer(String message){
        switch(message.split(":")[0]){
            case "temperature":
                break;
            case "trashlevel" :
                break;
        }
    }




    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
