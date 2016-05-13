package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    //xLoc, yLoc are used to drag the window.
    private double xLoc = 0;
    private double yLoc = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main_gui.fxml"));
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

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();


    }
}
