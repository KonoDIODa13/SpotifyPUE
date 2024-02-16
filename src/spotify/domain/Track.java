package spotify.domain;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor
@Data
public class Track {
    /*
     Title
     Artist(s)
     Album
     Duration
     Genre
     Year of release
     URL or location of the audio file
     Rating
     Lyrics (optional)
     User comments (optional)
     */
    private long id;
    private String title;
    private List<String> artists;
    private List<String> genres;
    private LocalDate releaseDate;
    private String url;
    private int seconds;

}