package simple_notes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.ResourceBundle;


/**
 * Created by ADDAUI on 5/14/2016.
 * this class is the Setting Controller.
 */
public class SettingController implements Initializable {

    //LogID and sample.Log class are used only for logging purposes.
    private static final String logID = "Dev_ADDAUI";
    //Defining Setting Gui Elements
    @FXML
    AnchorPane anchor;
    @FXML
    MenuButton language;
    @FXML
    MenuButton theme;
    @FXML
    Label gmtOffset;
    @FXML
    Slider gmtSlider;
    @FXML
    TextField dateFormat;
    @FXML
    Label dfExample;
    @FXML
    TextField timeFormat;
    @FXML
    Label tfExample;
    @FXML
    Label dfError;
    @FXML
    Label tfError;
    @FXML
    Hyperlink link;
    @FXML
    Button cancelBtn;
    @FXML
    Button okButton;
    //format OK
    private boolean dateOK = true;
    private boolean timeOK = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadConfig();

        //Cancel Button Action.
        cancelBtn.setOnAction(event -> {
            Controller.settingStage.close();
            Controller.settingStage = null;
        });

        //Ok Button Action.
        okButton.setOnAction(event -> {
            saveConfig();
            cancelBtn.fire();
            Main.applyConfig();
        });

        //Language Menu Listener.
        for (MenuItem menuItem : language.getItems()) {
            menuItem.setOnAction(value -> {
                language.setText(menuItem.getText());
                Log.i(logID, menuItem.getId() + " selected");
            });
        }

        //Theme Menu Listener.
        for (MenuItem menuItem : theme.getItems()) {
            menuItem.setOnAction(value -> {
                theme.setText(menuItem.getText());
                Log.i(logID, menuItem.getId() + " selected");
            });
        }

        //GMT Slider Listener
        gmtSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int value = Math.round(newValue.floatValue());
            Log.d(logID, String.format("Old Value: %.2f , New Value: %d\n", oldValue.floatValue(), value));
            gmtOffset.setText((value == 0) ? "GMT" : (value > 0) ? "GMT+" + value : "GMT" + value);
        });

        //Date Format
        dateFormat.textProperty().addListener(listener -> {
            Date date = Date.from(Instant.now());
            try {
                //Validate date format characters
                if (!(dateFormat.getText().matches("^[yYMwWDdFEu\\s\\p{Punct}]+$") || dateFormat.getText().equals("")))
                    throw new IllegalArgumentException("Only Date format and/or symbols allowed!");
                DateFormat df;
                if (dateFormat.getText().equals("")) {
                    df = new SimpleDateFormat(dateFormat.getPromptText());
                } else {
                    df = new SimpleDateFormat(dateFormat.getText());
                }
                dfError.setText("");
                dfError.setOpacity(0.00);
                System.out.println(df.format(date));
                dfExample.setText(df.format(date));
                dateOK = true;
            } catch (IllegalArgumentException e) {
                dateOK = false;
                dfExample.setText("");
                dfError.setText(Config.lang.getString("dformat_error"));
                dfError.setOpacity(1.00);
            }
        });

        //Time Format
        timeFormat.textProperty().addListener(listener -> {
            Date date = Date.from(Instant.now());
            try {
                //Validate time format characters
                if (!(timeFormat.getText().matches("^[aHkKhmsS\\s\\p{Punct}]+$") || timeFormat.getText().equals("")))
                    throw new IllegalArgumentException("Only time format and/or   allowed!");
                DateFormat tf;
                if (timeFormat.getText().equals("")) {
                    tf = new SimpleDateFormat(timeFormat.getPromptText());
                } else {
                    tf = new SimpleDateFormat(timeFormat.getText());
                }
                tfError.setText("");
                tfError.setOpacity(0.00);
                System.out.println(tf.format(date));
                tfExample.setText(tf.format(date));
                timeOK = true;
            } catch (IllegalArgumentException e) {
                timeOK = false;
                tfExample.setText("");
                tfError.setText(Config.lang.getString("tformat_error"));
                tfError.setOpacity(1.00);
            }
        });

        //url link clicked action
        link.setOnAction(value -> {
            try {
                java.awt.Desktop.getDesktop().browse(new URI(link.getText()));
            } catch (Exception e) {
                Log.e(logID, "Error Opening Browser");
            }

        });
    }

    private void saveConfig() {

        if (timeOK && dateOK) {

            //Save Language
            if (language.getText().equals("English")) {
                Config.setLanguage("en");
            } else {
                Config.setLanguage("ar");
            }
            //Save theme
            if (theme.getText().equals("Light")) {
                Config.setTheme("Light");
            } else {
                Config.setTheme("Dark");
            }

            //Save GMT offset
            Config.setGMTOffset(gmtOffset.getText());
            //Save Date Format
            Config.setDateFormat((dateFormat.getText().equals("")) ? dateFormat.getPromptText() : dateFormat.getText());
            //Save Time Format
            Config.setTimeFormat((timeFormat.getText().equals("")) ? timeFormat.getPromptText() : timeFormat.getText());

            //Save Config to a File
            Config.saveConfig();
        }
    }

    private void loadConfig() {

        //Load Language
        for (MenuItem item : language.getItems()) {
            RadioMenuItem rmi = (RadioMenuItem) item;
            if (Config.getLanguage().equals(rmi.getId())) {
                rmi.setSelected(true);
                language.setText(rmi.getText());
            }
        }
        //Load Theme
        for (MenuItem item : theme.getItems()) {
            RadioMenuItem rmi = (RadioMenuItem) item;
            if (Config.getTheme().equals(rmi.getText())) {
                rmi.setSelected(true);
                theme.setText(rmi.getText());
            }
        }

        //Load GMT Offset
        if (Config.getGMTOffset().equals("GMT")) {
            gmtSlider.setValue(0.0);
        } else {
            gmtSlider.setValue(Double.valueOf(Config.getGMTOffset().substring(3)));
        }
        gmtOffset.setText(Config.getGMTOffset());
        //Load Date Format
        dateFormat.setPromptText(Config.getDateFormat());
        //Load Time Format
        timeFormat.setPromptText(Config.getTimeFormat());
    }


}
