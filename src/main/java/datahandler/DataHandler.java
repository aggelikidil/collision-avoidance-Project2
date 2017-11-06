package datahandler;

import data.DataSettings;
import java.io.*;


public class DataHandler {
    /**
     *              διάβασμα απο τον δίσκο
     *
     * @return              Επιστρέφει τις ρυθμίσεις της εφαρμογής
     */
    public DataSettings loadFromDisk() { //διάβασμα απο τον δίσκο
        DataSettings data = new DataSettings();

        try { //αν υπάρχει αρχείο διάβασε απο αυτό
            File file = new File("config.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fileInputStream);

            data.firstsensorvalue = reader.read();
            data.secondsensorvalue_x = reader.read();
            data.secondsensorvalue_y = reader.read();
            data.secondsensorvalue_z = reader.read();

            reader.close();
            fileInputStream.close();

            System.out.println("Settings loaded successfully");
            return data;
        } catch (IOException e) { //διαφορετικα επέστρεψε τις αρχικές τιμές του datasettings
            System.out.println("Settings not found. Loading the defaults.");
            return data;
        }
    }

    /**
     *              ανάγνωση απο τον δίσκο
     *
     * @param   data     to settinsgs
     * **/

    public void saveToDisk(DataSettings data) {
        try {
            //αν υπάρχει το αρχείο γράψε σε αυτό
            File file = new File("config.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);

            //αλλαγή τιμών στο datasettings
            outputStreamWriter.write(data.firstsensorvalue);
            outputStreamWriter.write(data.secondsensorvalue_x);
            outputStreamWriter.write(data.secondsensorvalue_y);
            outputStreamWriter.write(data.secondsensorvalue_z);
            fileOutputStream.flush();



            outputStreamWriter.close();
            fileOutputStream.close();

            System.out.println("Settings Saved");


        } catch (IOException e) { //error
            e.printStackTrace();
        }
    }

}