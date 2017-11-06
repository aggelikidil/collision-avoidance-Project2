package parameterhandler;

import parameter.ParameterSettings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ParameterHandler {
    /**
     *
     * @return parameters oi parametroi gia ta MySql mosquito kai ta ids ton termatikon
     */
    public ParameterSettings loadFromDisk() { //διάβασμα απο τον δίσκο
        ParameterSettings parameters = new ParameterSettings();
        Properties prop = new Properties();
        InputStream input = null;
        try { //αν υπάρχει αρχείο διάβασε απο αυτό
            // anoixei to arxeio config.properties mesa sto resources ..
            input = new FileInputStream("src/main/resources/config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and save it to object parameters
            parameters.mysql_databasename = prop.getProperty("MYSQL_DATABASENAME");
            parameters.mysql_ip = prop.getProperty("MYSQL_IP");
            parameters.mysql_port = prop.getProperty("MYSQL_PORT");
            parameters.mysql_username = prop.getProperty("MYSQL_USERNAME");
            parameters.mysql_password = prop.getProperty("MYSQL_PASSWORD");
            parameters.mysql_ip = prop.getProperty("MYSQL_IP");
            parameters.mqtt_ip = prop.getProperty("MQTT_IP");
            parameters.mqtt_port = prop.getProperty("MQTT_PORT");
            parameters.topic1 = prop.getProperty("TOPIC1");
            parameters.topic2 = prop.getProperty("TOPIC2");
            parameters.clientid = prop.getProperty("CLIENTID");
            parameters.qos=Integer.parseInt(prop.getProperty(("QOS")));


            return parameters;

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return parameters;
    }
}