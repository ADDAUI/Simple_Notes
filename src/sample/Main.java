package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private static Stage stage = null;

    //xLoc, yLoc are used to drag the window.
    private double xLoc;
    private double yLoc;

    public static void main(String[] args) {
        Config.loadConfig();
        launch(args);
    }

    static void applyConfig() {

        //Apply Language
        //TODO

        stage.close();
        stage = new Stage();
        Main main = new Main();
        try {
            main.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        buildStage(primaryStage, "res/main_gui.fxml", ("styles/main_" + Config.getTheme().toLowerCase() + ".css"));
        stage = primaryStage;
        primaryStage.show();
    }

    private void buildStage(Stage primaryStage, String fxml, String css) throws Exception {
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

        primaryStage.setScene(new Scene(root, 800, 600));

    }

}
