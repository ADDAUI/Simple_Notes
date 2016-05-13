package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static final String logID = "Dev_ADDAUI";

    private NotesList notesList = new NotesList();
    private TreeItem<Note> rootItem = new TreeItem<Note>(new Note("Quick Notes", ""));

    //Defining Gui Elements
    @FXML Label notification;
    @FXML TreeView<Note> tree;
    @FXML Button saveBtn;
    @FXML Button newBtn;
    @FXML Button deleteBtn;
    @FXML TextField title;
    @FXML TextArea content;
    @FXML Label date_created;
    @FXML Label time_created;
    @FXML Label date_updated;
    @FXML Label time_updated;
    @FXML AnchorPane note_panel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Log.setState('i');

        rootItem.setExpanded(true);
        tree.setRoot(rootItem);
        tree.refresh();

        if (tree.getSelectionModel().isEmpty()) {
            note_panel.setDisable(true);
        } else {
            note_panel.setDisable(false);
        }

        newBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO flash a notification if created, and maybe save the current note before creating a new one
                saveBtn.fire();
                notesList.newNote("New Note", "");
                TreeItem<Note> item = new TreeItem<Note>(notesList.getNote(notesList.getSize() - 1));
                rootItem.getChildren().add(item);
                tree.getSelectionModel().select(item);
                Log.i(logID, "\n" + notesList.toString());
            }
        });

        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO flash a notification if saved
                if(!tree.getSelectionModel().isEmpty()) {
                    String newTitle = title.getText();
                    String newContent = content.getText();
                    notesList.getNote(tree.getSelectionModel().getSelectedIndex()).updateNote(newTitle, newContent, Date.from(Instant.now()));
                    tree.refresh();
                }
            }
        });

        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO Flash a notification if the tree empty
                if (rootItem.getChildren().size() == 0) {
                    Log.w(logID, "Hey the tree is empty, stop pushing!!");
                    return;
                }
                int index = tree.getSelectionModel().getSelectedIndex();
                notesList.deleteNote(index);
                rootItem.getChildren().remove(index);
                if (rootItem.getChildren().size() == 0) {
                    note_panel.setDisable(true);
                }
                tree.refresh();
            }
        });

        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Note>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Note>> observable, TreeItem<Note> oldValue, TreeItem<Note> newValue) {
                Log.v(logID, "Controller.changed");
                Log.v(logID, "observable = [" + observable + "], oldValue = [" + oldValue + "], newValue = [" + newValue + "]");

                if (newValue != null) {
                    updateDetails(newValue.getValue());
                }

                note_panel.setDisable(false);
            }
        });


    }

    public void updateDetails(Note note) {

        title.setText(note.getTitle());
        content.setText(note.getContent());
        date_created.setText(note.getCreated().toLocaleString().substring(0, 12));
        time_created.setText(note.getCreated().toLocaleString().substring(13, 23));
        date_updated.setText(note.getUpdated().toLocaleString().substring(0, 12));
        time_updated.setText(note.getUpdated().toLocaleString().substring(13, 23));


    }
}
