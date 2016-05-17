package simple_notes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    //File variables
    static final String fs = System.getProperty("file.separator");
    //stage is static to make sure it have only one instance(class variable)
    private static Stage stage = null;
    //xLoc, yLoc are used to drag the window.
    private double xLoc;
    private double yLoc;

    public static void main(String[] args) {

        mkResDir(Config.getResPath());

        Config.loadConfig();
        launch(args);
    }

    static void applyConfig() {
        stage.close();
        stage = new Stage();
        Main main = new Main();
        try {
            main.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Make the path Directory by starting from the root.
    private static boolean mkResDir(String path) {
        String[] steps = split(path, fs.charAt(0));
        File file = new File(path);
        String dirPath = "";
        int counter = steps.length - 1;
        while (!file.exists() || counter >= 0) {
            dirPath = dirPath + steps[counter] + fs;
            File dir = new File(dirPath);
            if (!dir.exists()) {
                System.out.println(dir.getPath() + " Doesn't exist!!");
                if (dir.mkdir()) {
                    System.out.println(dir.getPath() + " has been created!!");
                }
            }
            counter--;
        }
        return file.exists();
    }

    //Split path into directory names.
    private static String[] split(String string, char splitter) {
        ArrayList<String> list = new ArrayList<>();
        while (string.lastIndexOf(splitter) != -1) {
            int result = string.lastIndexOf(splitter);
            list.add(string.substring(result + 1));
            string = string.substring(0, result);
        }
        if (!string.equals(fs))
            list.add(string);

        String[] str = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            str[i] = list.get(i);
        }
        return str;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        buildStage(primaryStage, "res/main_gui_" + Config.getLanguage() + ".fxml", ("styles/main_" + Config.getTheme().toLowerCase() + ".css"));
        stage = primaryStage;
        primaryStage.show();
    }

    private void buildStage(Stage primaryStage, String fxml, String css) throws Exception {
        Config.lang = ResourceBundle.getBundle("simple_notes/res/lang", new Locale(Config.getLanguage()));

        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        primaryStage.setTitle("Simple Notes");
        primaryStage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            xLoc = event.getSceneX();
            yLoc = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xLoc);
            primaryStage.setY(event.getScreenY() - yLoc);
        });

        root.getStylesheets().clear();
        root.getStylesheets().add(getClass().getResource(css).toString());

        primaryStage.setScene(new Scene(root));

    }
}
