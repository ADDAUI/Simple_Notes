package sample;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

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
    //Defining Gui Elements
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
    //xLoc, yLoc are used to drag the window.
    private double xLoc = 0;
    private double yLoc = 0;
    //animation
    private FadeTransition fadeIn = new FadeTransition(new Duration(2000));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Log.setState('i');

        rootItem.setExpanded(true);
        tree.setRoot(rootItem);
        tree.refresh();

        if(notesList.loadNotes()){
            for (Note note: notesList) {
                rootItem.getChildren().add(new TreeItem<>(note));
            }
            tree.refresh();
        }

        if (tree.getSelectionModel().isEmpty()) {
            note_panel.setDisable(true);
        } else {
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
                updateDetails(notesList.getNote(tree.getSelectionModel().getSelectedIndex()));
                notesList.saveNotes();
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
                notesList.saveNotes();
            }
            if (rootItem.getChildren().size() == 0) {
                title.clear();
                content.clear();
                date_created.setText("Jan 00, 0000");
                date_updated.setText("Jan 00, 0000");
                time_created.setText("00:00:00 AM");
                time_updated.setText("00:00:00 AM");
                note_panel.setDisable(true);
            }
            setNotification("Note Deleted");
            tree.refresh();
        });

        settingBtn.setOnAction(event -> {
            //TODO Open Setting Window
        });

        closeBtn.setOnAction(event -> Platform.exit());

        tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Log.v(logID, "Controller.changed");
            Log.v(logID, "observable = [" + observable + "], oldValue = [" + oldValue + "], newValue = [" + newValue + "]");

            if (newValue != null) {
                updateDetails(newValue.getValue());
            }

            note_panel.setDisable(false);
        });
    }

    private void updateDetails(Note note) {

        DateFormat date = new SimpleDateFormat("MMM dd, yyyy");
        date.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        DateFormat time = new SimpleDateFormat("hh:mm:ss aa");
        time.setTimeZone(TimeZone.getTimeZone("GMT+3"));

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
