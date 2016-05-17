package simple_notes;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * Created by ADDAUI on 5/12/2016.
 * This class contain all information needed for a note object.
 */
public class Note implements Serializable {

    static final long serialVersionUID = 1L;

    private String title;
    private String content;
    private Date created;
    private Date updated;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.updated = this.created = Date.from(Instant.now());
    }

    boolean updateNote(String title, String content, Date updated) {
        this.title = title;
        this.content = content;
        this.updated = updated;
        return true;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getContent() {
        return content;
    }

    void setContent(String content) {
        this.content = content;
    }

    Date getCreated() {
        return created;
    }

    Date getUpdated() {
        return updated;
    }

    @Override
    public String toString() {
        return getTitle();
    }

}
