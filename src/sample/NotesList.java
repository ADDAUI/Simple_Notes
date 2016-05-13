package sample;

import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.util.LinkedList;

/**
 * Created by ADDAUI on 5/12/2016.
 */
public class NotesList implements Serializable {

    private static LinkedList<Note> notes = new LinkedList<>();

    //add a new note
    public boolean newNote(String title,String content){
        notes.add(new Note(title,content));
        return true;
    }

    //save a note
    public boolean saveNote(int position,String title, String content){
        notes.get(position).updateNote(title,content, Date.from(Instant.now()));
        return true;
    }

    public boolean deleteNote(int position){
        notes.remove(position);
        return true;
    }

    public Note getNote(int position){
        return notes.get(position);
    }

    public int getSize(){
        return notes.size();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i=0;i<getSize();i++){
            string.append("Position : "+i+"\tNote : "+getNote(i)+"\n");
        }
        return string.toString();
    }
}
