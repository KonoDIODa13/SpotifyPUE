package spotify.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data

public class Playlist implements Comparable<Playlist> {

    private long id;
    private String title;
    private String description;
    private List<String> comments;
    private LocalDate creationDate;

    public Playlist(long id, String title, String description, List<String> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.comments = comments;
        this.creationDate = LocalDate.now();
    }


    @Override
    public int compareTo(Playlist p) {
        return this.creationDate.compareTo(p.creationDate);
    }
}
