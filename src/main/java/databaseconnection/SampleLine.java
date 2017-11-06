package databaseconnection;

import java.util.Date;

public class SampleLine {
    public int LINE_id;
    public String terminal_id;
    public String sensortype1;
    public int no_of_values1;
    public float sensorvalue1;
    public String unit_sensor1;
    public String sensortype2;
    public int no_of_values2;
    public float sensorvalue2x;
    public float sensorvalue2y;
    public float sensorvalue2z;
    public String unit_sensor2;
    public Date datetime;
    public float latitude;
    public float longtitude;
    public boolean confirmed_collision;

    @Override
    public String toString() {
        return LINE_id + ") " + terminal_id + ", Proximity value = " +sensorvalue1 +"(" + unit_sensor1 +")"+
                ", Accelerometer value x = " + sensorvalue2x +
                ", y = " + sensorvalue2y +
                ", z = " + sensorvalue2z + "(" +
                unit_sensor2 + ")" + datetime +
                ", Latitude = " + latitude +
                ", Longtitude = " + longtitude +
                ", Confirmed = " + confirmed_collision;
    }
}