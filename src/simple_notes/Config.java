package simple_notes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import static simple_notes.Main.fs;

/**
 * Created by ADDAUI on 5/14/2016.
 * This class store the Setting Configurations
 */
class Config {

    //LogID and sample.Log class are used only for logging purposes.
    private static final String logID = "Dev_ADDAUI";
    static ResourceBundle lang;
    //Settings are initialized to the default setting.
    private static String Language = "en";
    private static String DateFormat = "MMM dd, yyyy";
    private static String TimeFormat = "hh:mm:ss aa";
    private static String Theme = "Light";
    private static String GMTOffset = "GMT+3";
    private static int LastSelectedNote = -1;
    private static char logLevel = 'w';
    private static boolean saveOnExit = false;
    private static String resPath = System.getProperty("user.home") + fs + "Simple Notes" + fs + "res";


    static String getResPath() {
        return resPath;
    }

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

    static char getLogLevel() {
        return logLevel;
    }

    static void setLogLevel(char logLevel) {
        Config.logLevel = logLevel;
    }

    static boolean getSaveOnExit() {
        return saveOnExit;
    }

    static void setSaveOnExit(boolean saveOnExit) {
        Config.saveOnExit = saveOnExit;
    }

    static void saveConfig() {
        try {
            String propFilePath = getResPath() + fs + "config.properties";
            FileInputStream in = new FileInputStream(propFilePath);
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream(propFilePath);
            props.setProperty("SaveOnExit", String.valueOf(getSaveOnExit()));
            props.setProperty("LogLevel", String.valueOf(logLevel));
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
            File configFile = new File(getResPath() + fs + ("config.properties"));
            if (configFile.createNewFile()) {
                System.out.println("config.properties has been created !!!");
                saveConfig();
            }

            String propFilePath = getResPath() + fs + "config.properties";
            FileInputStream in = new FileInputStream(propFilePath);
            Properties props = new Properties();
            props.load(in);
            in.close();

            setLogLevel(props.getProperty("LogLevel").charAt(0));
            Log.setState(getLogLevel());
            setSaveOnExit(Boolean.valueOf(props.getProperty("SaveOnExit")));
            setLanguage(props.getProperty("Language"));
            setDateFormat(props.getProperty("DateFormat"));
            setTimeFormat(props.getProperty("TimeFormat"));
            setTheme(props.getProperty("Theme"));
            setGMTOffset(props.getProperty("GMTOffset"));
            setLastSelectedNote(Integer.valueOf(props.getProperty("LastSelectedNote")));
        } catch (Exception e) {
            Log.e(logID, e.getMessage());
        }

        return false;
    }

}
