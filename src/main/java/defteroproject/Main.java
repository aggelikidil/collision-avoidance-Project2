package defteroproject;

import administratorcontroller.AdminController;
import data.DataSettings;
import datahandler.DataHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parameter.ParameterSettings;
import parameterhandler.ParameterHandler;
import singleton.SharedMemory;

public class Main extends Application {
    //                  Settings
    DataSettings dataSettings;
    ParameterSettings parameterSettings;

    SharedMemory sharedMemory;

    //                  Admin controller thread
    AdminController adminController = new AdminController();

    @Override
    public void start(Stage primaryStage) throws Exception{
        ParameterHandler handler = new ParameterHandler();
        DataHandler handler2 = new DataHandler();

        //                Read settings from disk
        sharedMemory = SharedMemory.getInstance();

        parameterSettings = handler.loadFromDisk();
        dataSettings = handler2.loadFromDisk();

        sharedMemory.setDataSettings(dataSettings);
        sharedMemory.setParameterSettings(parameterSettings);

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("Deftero project");
        primaryStage.setScene(new Scene(root, 800, 700));


        System.out.println("MySQL data base name is " + parameterSettings.mysql_databasename);
        System.out.println("MySQL IP is " + parameterSettings.mysql_ip);
        System.out.println("MySQL port is " + parameterSettings.mysql_port);
        System.out.println("MySQL username is " + parameterSettings.mysql_username);
        System.out.println("MySQL password is " + parameterSettings.mysql_password);
        System.out.println("MQTT IP is " + parameterSettings.mqtt_ip);
        System.out.println("MQTT port is " + parameterSettings.mqtt_port);
        System.out.println("First topic's name is " + parameterSettings.topic1);
        System.out.println("Second topic's name is" + parameterSettings.topic2);

        // start admin thread
        adminController.start();

        // main thread
        primaryStage.show();

    }

    @Override
    public void stop() throws Exception {
        super.stop();

        adminController.interrupt();
        adminController.join();

        DataHandler handler2 = new DataHandler();
        handler2.saveToDisk(dataSettings);
    }

    public static void main(String[] args) {

     launch(args);



    }
}
