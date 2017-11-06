package administratorcontroller;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import parameter.ParameterSettings;
import singleton.SharedMemory;


public class AdminController extends Thread {

    //admin thread
    public void run() {
        //prosvasi sthn mnhmh
        SharedMemory sharedMemory = SharedMemory.getInstance();
        ParameterSettings parameterSettings = sharedMemory.getParameterSettings();

        String broker = "tcp://" + parameterSettings.mqtt_ip + ":" + parameterSettings.mqtt_port;
        String clientId = parameterSettings.clientid;
        int qos = parameterSettings.qos;

        String topic1 = parameterSettings.topic1;
        String topic2 = parameterSettings.topic2;

        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

        MosquitoControllerTerminal x = new MosquitoControllerTerminal();

        MqttClient sampleClient = null;

        System.out.println("Admin controller has started");

        try {
            // subscribe
            sampleClient = new MqttClient(broker, clientId, persistence);
            sampleClient.setCallback(x);

            System.out.println("Connecting to broker to topic of terminal: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected to broker");

            System.out.println("Subscrib" +
                    "ing to topic " + topic1);
            sampleClient.subscribe(topic1, qos);
            System.out.println("Ok, subscription successful to topic " + topic1);
            System.out.println("Subscribing to topic " + topic2);
            sampleClient.subscribe(topic2, qos);
            System.out.println("Ok, subscription successful to topic " + topic2);

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg" + me.getMessage());
            System.out.println("loc" + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep" + me);
            me.printStackTrace();
            System.exit(-1);
        }

        while (!isInterrupted()) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Admin controller has been interrupted");
                break;
            }
        }

        try {
            if (sampleClient != null) {
                sampleClient.unsubscribe(topic1);
                System.out.println("Ok, subscription cancelled to topic " + topic1);
                sampleClient.unsubscribe(topic2);
                System.out.println("Ok, subscription cancelled to topic " + topic2);
            }
        } catch(Exception e) {

        }

        System.out.println("Admin controller has stopped");
    }
}


