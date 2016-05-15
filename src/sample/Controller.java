package sample;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class Controller implements Initializable {

    //LogID and sample.Log class are used only for logging purposes.
    private static final String logID = "Dev_ADDAUI";
    //Setting Stage
    static Stage settingStage;
    //Defining Main GUI Elements
    @FXML
    AnchorPane mainPanel;
    @FXML Label notification;
    @FXML TreeView<Note> tree;
    @FXML Button saveBtn;
    @FXML Button newBtn;
    @FXML Button deleteBtn;
    @FXML
    Button settingBtn;
    @FXML
    Button closeBtn;
    @FXML TextField title;
    @FXML TextArea content;
    @FXML Label date_created;
    @FXML Label time_created;
    @FXML Label date_updated;
    @FXML Label time_updated;
    @FXML AnchorPane note_panel;
    @FXML HBox notification_panel;
    //notesList are the note list.
    private NotesList notesList = new NotesList();
    private TreeItem<Note> rootItem = new TreeItem<>(new Note("Quick Notes", ""));
    //animation
    private FadeTransition fadeIn = new FadeTransition(new Duration(2000));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Log.setState('i');

        newBtn.tooltipProperty().set(new Tooltip("Add a new note"));
        saveBtn.tooltipProperty().set(new Tooltip("Save selected note"));
        deleteBtn.tooltipProperty().set(new Tooltip("Delete selected note"));
        settingBtn.tooltipProperty().set(new Tooltip("Open settings"));
        closeBtn.tooltipProperty().set(new Tooltip("Exit"));


        rootItem.setExpanded(true);
        tree.setRoot(rootItem);
        tree.refresh();

        if(notesList.loadNotes()){
            for (Note note: notesList) {
                rootItem.getChildren().add(new TreeItem<>(note));
            }
            tree.getSelectionModel().select(Config.getLastSelectedNote());
            tree.refresh();
        }

        if (tree.getSelectionModel().isEmpty()) {
            note_panel.setDisable(true);
        } else {
            updateDetails();
            note_panel.setDisable(false);
        }

        newBtn.setOnAction(event -> {
            if(!tree.getSelectionModel().isEmpty()) {
                saveBtn.fire();
            }
            notesList.newNote("New Note", "");
            TreeItem<Note> item = new TreeItem<>(notesList.getNote(notesList.getSize() - 1));
            rootItem.getChildren().add(item);
            tree.getSelectionModel().select(item);
            setNotification("New Note Created");
            Log.i(logID, "\n" + notesList.toString());
        });

        saveBtn.setOnAction(event -> {
            if (rootItem.getChildren().size() == 0) {
                Log.w(logID, "Hey the tree is empty!!");
                setNotification("No Note Selected !!!");
                return;
            }
            if(!tree.getSelectionModel().isEmpty()) {
                String newTitle = title.getText();
                String newContent = content.getText();
                notesList.getNote(tree.getSelectionModel().getSelectedIndex()).updateNote(newTitle, newContent, Date.from(Instant.now()));
                updateDetails();
                notesList.saveNotes();
                Config.saveConfig();
                tree.refresh();
                setNotification("Note Saved");
            }
        });

        deleteBtn.setOnAction(event -> {
            if (rootItem.getChildren().size() == 0) {
                Log.w(logID, "Hey the tree is empty!!");
                setNotification("No Note Selected!");
                return;
            }
            if(!tree.getSelectionModel().isEmpty()) {
                int index = tree.getSelectionModel().getSelectedIndex();
                notesList.deleteNote(index);
                rootItem.getChildren().remove(index);
                setNotification("Note Deleted");
                notesList.saveNotes();
            }
            if (rootItem.getChildren().size() == 0) {
                title.clear();
                content.clear();
                date_created.setText("                    ");
                date_updated.setText("                    ");
                time_created.setText("                    ");
                time_updated.setText("                    ");
                note_panel.setDisable(true);
            }
            tree.refresh();
        });

        settingBtn.setOnAction(event -> {
            if (settingStage != null) {
                settingStage.requestFocus();
                return;
            }

            try {
                Parent root;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("res/setting.fxml"));
                root = loader.load();
                settingStage = new Stage();
                settingStage.setTitle("Setting");
                settingStage.initStyle(StageStyle.UNDECORATED);
                settingStage.setScene(new Scene(root));
                settingStage.show();
            } catch (IOException e) {
                Log.e(logID, e.getMessage());
                e.printStackTrace();
            }

        });


        closeBtn.setOnAction(event -> {
            saveBtn.fire();
            Platform.exit();
        });

        tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Log.v(logID, "Controller.changed");
            if (newValue != null) {
                updateDetails();
                Config.setLastSelectedNote(tree.getSelectionModel().getSelectedIndex());
            }

            note_panel.setDisable(false);
        });
    }

    private void updateDetails() {
        Note note = tree.getSelectionModel().getSelectedItem().getValue();

        DateFormat date = new SimpleDateFormat(Config.getDateFormat());
        date.setTimeZone(TimeZone.getTimeZone(Config.getGMTOffset()));
        DateFormat time = new SimpleDateFormat(Config.getTimeFormat());
        time.setTimeZone(TimeZone.getTimeZone(Config.getGMTOffset()));

        title.setText(note.getTitle());
        content.setText(note.getContent());
        date_created.setText(date.format(note.getCreated()));
        time_created.setText(time.format(note.getCreated()));
        date_updated.setText(date.format(note.getUpdated()));
        time_updated.setText(time.format(note.getUpdated()));

    }

    private void setNotification(String message){
        notification.setText(message);
        fadeIn.setNode(notification_panel);
        fadeIn.setFromValue(0.00);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(2);
        fadeIn.setAutoReverse(true);
        fadeIn.playFromStart();
        fadeIn.setOnFinished(value -> notification.setText(""));
    }


}
