package javafxcontroller;

import data.DataSettings;
import databaseconnection.Queries;
import databaseconnection.SampleLine;
import databaseconnection.SampleTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import parameter.ParameterSettings;
import singleton.SharedMemory;
import javax.swing.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class FxController implements Initializable {
    private SharedMemory sharedMemory;
    private ParameterSettings parameterSettings;
    private DataSettings dataSettings;

    //settings tab
    @FXML
    public TextField proximity_value_setting;

    @FXML
    public TextField accelerometerx_value_setting;

    @FXML
    public TextField accelerometery_value_setting;

    @FXML
    public TextField accelerometerz_value_setting;

    @FXML
    public TextField ID_1;

    @FXML
    public TextField ID_2;


// search tab

    @FXML
    public CheckBox terminal_1;
    @FXML
    public CheckBox terminal_2;
    @FXML
    public TextField proximity_value;
    @FXML
    public TextField minx;
    @FXML
    public TextField maxx;
    @FXML
    public TextField miny;
    @FXML
    public TextField maxy;
    @FXML
    public TextField minz;
    @FXML
    public TextField maxz;
    @FXML
    public TextField min_latitude;
    @FXML
    public TextField max_latitude;
    @FXML

    public TextField min_longitude;
    @FXML
    public TextField max_longitude;
    @FXML
    public DatePicker calendar_from;
    @FXML
    public DatePicker calendar_to;
    @FXML
    public TextField set_time_from;
    @FXML
    public TextField set_time_to;
    @FXML
    public CheckBox confirmed_collision;
    @FXML
    public CheckBox unconfirmed_collision;
    @FXML
    public TextArea results;

    /**
     * apothikeusi katoflion ston disko
     * @param actionEvent patima koumpiou Save
     */
    public void onSaveSettings(ActionEvent actionEvent) {
        SharedMemory memory = SharedMemory.getInstance();
        DataSettings dataSettings = memory.getDataSettings();

        try {
            String spv = proximity_value_setting.getText();
            String savx = accelerometerx_value_setting.getText();
            String savy = accelerometery_value_setting.getText();
            String savz = accelerometerz_value_setting.getText();

            int pv = Integer.parseInt(spv);
            int avx = Integer.parseInt(savx);
            int avy = Integer.parseInt(savy);
            int avz = Integer.parseInt(savz);

            //diamoiazomeni mnimi
            synchronized (dataSettings) {
                dataSettings.firstsensorvalue = pv;
                dataSettings.secondsensorvalue_x = avx;
                dataSettings.secondsensorvalue_y = avy;
                dataSettings.secondsensorvalue_z = avz;
            }

            System.out.println("settings saved");
        } catch (Exception e) {
            e.printStackTrace();
            //warning icon
            JOptionPane.showMessageDialog(null,
                    "Invalid input. Try again!",
                    "Inane warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    //metatropi sting se float
    private Float FloatparseFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * anazitisi sthn vasi dedomenon
     * @param actionEvent patima tou koumpiou Search
     */
    public void onSearchDatabase(ActionEvent actionEvent) {
        // read parameters from GUI
        try {
            String st1 = terminal_1.isSelected() ? parameterSettings.topic1 : null; // ok
            String st2 = terminal_2.isSelected() ? parameterSettings.topic2 : null; // ok
            String spr = proximity_value.getText(); // ok
            String smix = minx.getText();
            String smax = maxx.getText();
            String smiy = miny.getText();
            String smay = maxy.getText();
            String smiz = minz.getText();
            String smaz = maxz.getText();
            String smil = min_latitude.getText();
            String smal = max_latitude.getText();
            String smilo = min_longitude.getText();
            String smalo = max_longitude.getText();
            String stf = set_time_from.getText();
            String stt = set_time_to.getText();
            Boolean cc = confirmed_collision.isSelected()? true:null;
            Boolean uc = unconfirmed_collision.isSelected()?true:null;

            LocalDate localDate1 = calendar_from.getValue();
            String scf = null;
            if (localDate1 != null) {
                Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
                Date datescf1 = Date.from(instant1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                scf = format1.format(datescf1);
                if (stf == null || stf.isEmpty()) {
                    scf = scf + stf;
                }
            } else {
                stf = null;
            }

            LocalDate localDate2 = calendar_to.getValue();
            String sct = null;
            if (localDate2 != null) {
                Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
                Date datesct = Date.from(instant2);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                sct = format.format(datesct);
                if (sct == null || sct.isEmpty())
                    sct = sct + stt;
            } else {
                stt = null;
            }


            Float pr = FloatparseFloat(spr);
            Float mix = FloatparseFloat(smix);
            Float max = FloatparseFloat(smax);
            Float miy = FloatparseFloat(smiy);
            Float may = FloatparseFloat(smay);
            Float miz = FloatparseFloat(smiz);
            Float maz = FloatparseFloat(smaz);
            Float mil = FloatparseFloat(smil);
            Float mal = FloatparseFloat(smal);
            Float milo = FloatparseFloat(smilo);
            Float malo = FloatparseFloat(smalo);

            Queries q = new Queries(parameterSettings);

            SampleTable table = q.search(st1, st2, pr, mix, max, miy, may, miz, maz, mil, mal, milo, malo, scf, sct, stf, stt, cc, uc);

            String s = "";
            for (SampleLine l : table.lines) {
                s = s + l.toString() + "\n";
                System.out.println(l.toString());
            }

            // show results to GUI
            results.setText(s);


        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Invalid input. Try again!",
                    "Inane warning",
                    JOptionPane.WARNING_MESSAGE);

        }


    }

    public void initialize(URL location, ResourceBundle resources) {
        sharedMemory = SharedMemory.getInstance();
        dataSettings = sharedMemory.getDataSettings();
        parameterSettings = sharedMemory.getParameterSettings();


        int pv = dataSettings.firstsensorvalue;
        int avx = dataSettings.secondsensorvalue_x;
        int avy = dataSettings.secondsensorvalue_y;
        int avz = dataSettings.secondsensorvalue_z;

        String t1 = parameterSettings.topic1;
        String t2 = parameterSettings.topic2;

        proximity_value_setting.setText(String.valueOf(pv));
        accelerometerx_value_setting.setText(String.valueOf(avx));
        accelerometery_value_setting.setText(String.valueOf(avy));
        accelerometerz_value_setting.setText(String.valueOf(avz));
        ID_1.setText(t1);
        ID_2.setText(t2);

    }

    /**
     * katharismos gui
     * @param actionEvent patima koumpiou clear
     */
    public void OnClear(ActionEvent actionEvent) {
        terminal_1.setSelected(false);
        terminal_2.setSelected(false);
        proximity_value.setText(" ");
        minx.setText(" ");
        maxx.setText(" ");
        miny.setText(" ");
        maxy.setText(" ");
        minz.setText(" ");
        maxz.setText(" ");
        min_latitude.setText(" ");
        max_latitude.setText(" ");
        min_longitude.setText(" ");
        max_longitude.setText(" ");
        calendar_from.setValue(null);
        calendar_to.setValue(null);
        set_time_from.setText(" ");
        set_time_to.setText(" ");
        confirmed_collision.setSelected(false);
        unconfirmed_collision.setSelected(false);
        results.setText(" ");

    }
}