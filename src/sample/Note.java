package sample;

import java.time.Instant;
import java.util.Date;

/**
 * Created by ADDAUI on 5/12/2016.
 * This class contain all information needed for a note object.
 */
public class Note {

    private String title;
    private String content;
    private Date created;
    private Date updated;

    public Note(String title,String content) {
        this.title = title;
        this.content = content;
        this.updated = this.created = Date.from(Instant.now());
    }

    public boolean updateNote(String title,String content, Date updated){
        this.title = title;
        this.content = content;
        this.updated = updated;
        return true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
