package simple_notes;

import java.io.*;
import java.util.LinkedList;

import static simple_notes.Main.fs;

/**
 * Created by ADDAUI on 5/12/2016.
 * This Class is used to connect the TreeView with the Notes.
 */
class NotesList extends LinkedList<Note> {

    //LogID and sample.Log class are used only for logging purposes.
    private static final String logID = "Dev_ADDAUI";

    //add a new note
    boolean newNote(String title, String content) {
        this.add(new Note(title, content));
        return true;
    }

    boolean deleteNote(int position) {
        this.remove(position);
        return true;
    }

    Note getNote(int position) {
        return this.get(position);
    }

    int getSize() {
        return this.size();
    }

    void saveNotes() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Config.getResPath() + fs + ("notes.ser")));
            oos.writeObject(this);
            oos.flush();
            oos.close();
            Log.i(logID, "notes List Saved to a file");
        } catch (Exception e) {
            Log.e(logID, "Not saved due to an error : " + e.getMessage());
            e.getMessage();
            e.printStackTrace();
        }
    }

    boolean loadNotes() {
        try {
            File configFile = new File(Config.getResPath() + fs + ("notes.ser"));
            if (configFile.createNewFile()) {
                System.out.println("notes.ser has been created !!!");
                Config.saveConfig();
            }

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Config.getResPath() + fs + ("notes.ser")));
            this.addAll((NotesList) ois.readObject());
            ois.close();
            Log.i(logID, "Note Loaded from file");
            Log.v(logID, "Content are : \n" + this);
            return true;
        } catch (FileNotFoundException e) {
            Log.e(logID, "File not Found !!!, Creating a new one.");
            return false;
        } catch (Exception e) {
            Log.e(logID, "Not Loaded due to an error : " + e.getMessage());
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < getSize(); i++) {
            string.append("Position : ").append(i).append("\tNote : ").append(getNote(i)).append("\n");
        }
        return string.toString();
    }
}
