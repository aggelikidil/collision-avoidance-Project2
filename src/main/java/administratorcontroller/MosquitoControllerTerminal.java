package administratorcontroller;

import com.sun.jmx.snmp.Timestamp;
import data.DataSettings;
import data.SensorsData;
import databaseconnection.Queries;
import databaseconnection.SampleLine;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import parameter.ParameterSettings;
import singleton.SharedMemory;

import java.text.SimpleDateFormat;

public class MosquitoControllerTerminal implements MqttCallback {
    ParameterSettings parameterSettings;

    SharedMemory sharedMemory;
    DataSettings dataSettings;

    String term1id;
    String term2id;

    Queries queries;

    public MosquitoControllerTerminal() {
        sharedMemory = SharedMemory.getInstance();
        dataSettings = sharedMemory.getDataSettings();
        parameterSettings = sharedMemory.getParameterSettings();

        term1id = parameterSettings.topic1;
        term2id = parameterSettings.topic2;

        queries = new Queries(parameterSettings);
    }

    /**
            *
            *
            * @param  msg  to minima pou ginetai publish me t apotelesmata
 */
    private void publishToTerminals(String msg) {
        String topic        = "result";
        String content      = msg;
        int qos             = parameterSettings.qos;
        String broker       = "tcp://" + parameterSettings.mqtt_ip + ":" + parameterSettings.mqtt_port;
        String clientId     = parameterSettings.clientid + "out";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: "+content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic,message );
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }

    /**
            *
            *H sinartisi kaleitai automata se kathe lipsi minimatos apo kapoio kanali
            * @param  topic  to kanali ao to opoio lifthike to minima
            * @param  message   to minima pou lifthike me morfi : terminal01,proximity,1,8,cm,accelerometer,3,0,0,-9.81,m/s^2,50,60,2010-10-10 10:10:10
 */
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String time = new Timestamp(System.currentTimeMillis()).toString();
        String message_content = new String(message.getPayload());
        System.out.println("Time:\t" +time +"  Topic:\t" + topic +"\nMessage:\t" + message_content + message.getQos());

        int pv = 0;
        int avx = 0;
        int avy = 0;
        int avz = 0;

        // apofeygoume tautoxroni prosvasi sti diamoirazomeni mnimi
        synchronized (dataSettings) {
            pv = dataSettings.firstsensorvalue;
            avx = dataSettings.secondsensorvalue_x;
            avy = dataSettings.secondsensorvalue_y;
            avz = dataSettings.secondsensorvalue_z;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String [] eventvalues = message_content.split(",");

        SampleLine line = new SampleLine();

        try {
            line.terminal_id = eventvalues[0];
            line.sensortype1 = eventvalues[1];
            line.no_of_values1 = Integer.parseInt(eventvalues[2]);
            line.sensorvalue1 = Float.parseFloat(eventvalues[3]);
            line.unit_sensor1 = eventvalues[4];
            line.sensortype2 = eventvalues[5];
            line.no_of_values2 = Integer.parseInt(eventvalues[6]);
            line.sensorvalue2x = Float.parseFloat(eventvalues[7]);
            line.sensorvalue2y = Float.parseFloat(eventvalues[8]);
            line.sensorvalue2z = Float.parseFloat(eventvalues[9]);
            line.unit_sensor2 = eventvalues[10];
            line.latitude = Float.parseFloat(eventvalues[11]);
            line.longtitude = Float.parseFloat(eventvalues[12]);
            line.datetime = simpleDateFormat.parse(eventvalues[13]);
        } catch (Exception e) {
            System.out.println("Message trashed: reason" + e.getMessage());
        }

        try {
            System.out.println("Line received:   " + line);

            //elegxos sygkrousis meso ton katoflion kai eisagogi sthn vasi dedomenon
            if (line.sensorvalue1 <= dataSettings.firstsensorvalue && line.sensorvalue2z > dataSettings.secondsensorvalue_z) {
                System.out.println("thresholds exceeded!");

                if (line.terminal_id.equals(term1id)) {
                    boolean confirmed = false;
                    if (SensorsData.lastdate_second_terminal != null) {
                        float timedifference = line.datetime.getTime() -SensorsData.lastdate_second_terminal.getTime();
                        if (timedifference < 1000) {
                            confirmed = true;
                        }
                    }

                    int pk1 = queries.insert(line.terminal_id, line.sensortype1, line.no_of_values1, line.sensorvalue1, line.unit_sensor1, line.sensortype2, line.no_of_values2, line.sensorvalue2x, line.sensorvalue2y, line.sensorvalue2z, line.unit_sensor2, line.latitude, line.longtitude, line.datetime, confirmed);

                    if (confirmed) {
                        queries.update(SensorsData.pk2);
                    }

                    SensorsData.pk1 = pk1;
                    SensorsData.lastdate_first_terminal = line.datetime;

                    if (confirmed) {
                        System.out.println("BOTH CRASHED");
                        publishToTerminals("BOTH CRASHED");
                    } else {
                        System.out.println("FIRST TERMINAL CRASHED");
                        publishToTerminals(term1id);
                    }
                }

                if (line.terminal_id.equals(term2id)) {
                    boolean confirmed = false;
                    if (SensorsData.lastdate_first_terminal != null) {
                        float timedifference = line.datetime.getTime() -SensorsData.lastdate_first_terminal.getTime();
                        if (timedifference < 1000) {
                            confirmed = true;
                        }
                    }

                    int pk2 = queries.insert(line.terminal_id, line.sensortype1, line.no_of_values1, line.sensorvalue1, line.unit_sensor1, line.sensortype2, line.no_of_values2, line.sensorvalue2x, line.sensorvalue2y, line.sensorvalue2z, line.unit_sensor2, line.latitude, line.longtitude, line.datetime, confirmed);

                    if (confirmed) {
                        queries.update(SensorsData.pk1);
                    }

                    SensorsData.pk2 = pk2;
                    SensorsData.lastdate_second_terminal = line.datetime;

                    if (confirmed) {
                        System.out.println("BOTH CRASHED");
                        publishToTerminals("BOTH CRASHED");
                    } else {
                        System.out.println("SECOND TERMINAL CRASHED");
                        publishToTerminals(term2id);

                    }
                }
            } else {
                System.out.println("thresholds safe!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void connectionLost(Throwable throwable) {

    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("deliveryComplete!");
    }
}