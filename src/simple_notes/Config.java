package simple_notes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by ADDAUI on 5/14/2016.
 * This class store the Setting Configurations
 */
class Config implements Serializable {

    //LogID and sample.Log class are used only for logging purposes.
    private static final String logID = "Dev_ADDAUI";
    public static ResourceBundle lang;
    //Settings are initialized to the default setting.
    private static String Language = "en";
    private static String DateFormat = "MMM dd, yyyy";
    private static String TimeFormat = "hh:mm:ss aa";
    private static String Theme = "Light";
    private static String GMTOffset = "GMT+3";
    private static int LastSelectedNote = -1;


    static String getLanguage() {
        return Language;
    }

    static void setLanguage(String language) {
        Language = language;
    }

    static String getDateFormat() {
        return DateFormat;
    }

    static void setDateFormat(String dateFormat) {
        DateFormat = dateFormat;
    }

    static String getTimeFormat() {
        return TimeFormat;
    }

    static void setTimeFormat(String timeFormat) {
        TimeFormat = timeFormat;
    }

    static String getTheme() {
        return Theme;
    }

    static void setTheme(String theme) {
        Theme = theme;
    }

    static String getGMTOffset() {
        return GMTOffset;
    }

    static void setGMTOffset(String GMTOffset) {
        Config.GMTOffset = GMTOffset;
    }

    static int getLastSelectedNote() {
        return LastSelectedNote;
    }

    static void setLastSelectedNote(int lastSelectedNote) {
        LastSelectedNote = lastSelectedNote;
    }

    static void saveConfig() {
        try {
            String propFilePath = "src/simple_notes/res/config.properties";
            FileInputStream in = new FileInputStream(propFilePath);
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream(propFilePath);
            props.setProperty("Language", Language);
            props.setProperty("DateFormat", DateFormat);
            props.setProperty("TimeFormat", TimeFormat);
            props.setProperty("Theme", Theme);
            props.setProperty("GMTOffset", GMTOffset);
            props.setProperty("LastSelectedNote", String.valueOf(LastSelectedNote));
            props.store(out, null);
            out.close();
        } catch (Exception e) {
            Log.e(logID, e.getMessage());
        }

    }

    static boolean loadConfig() {
        try {

            String propFilePath = "src/simple_notes/res/config.properties";
            FileInputStream in = new FileInputStream(propFilePath);
            Properties props = new Properties();
            props.load(in);
            in.close();

            Config.Language = props.getProperty("Language");
            Config.DateFormat = props.getProperty("DateFormat");
            Config.TimeFormat = props.getProperty("TimeFormat");
            Config.Theme = props.getProperty("Theme");
            Config.GMTOffset = props.getProperty("GMTOffset");
            Config.LastSelectedNote = Integer.valueOf(props.getProperty("LastSelectedNote"));
        } catch (Exception e) {
            Log.e(logID, e.getMessage());
        }

        return false;
    }
}
