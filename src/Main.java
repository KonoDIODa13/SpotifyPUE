import spotify.application.Spotify;
import spotify.application.SpotifyImpl;
import spotify.domain.Playlist;
import spotify.domain.Track;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Java Team :)");
        Playlist playlist1 = new Playlist(1, "Chingonas", "Canciones Chingonas",
                List.of("Ole ole los caracoles", "Esto es un comentario"));

        Playlist playlist2 = new Playlist(2, "Chingonas", "Canciones Chingonas",
                List.of("Ole ole los caracoles", "Esto es un comentario"));

        Track track1 = new Track(1, "Bury the light", List.of("Casey Edwards", "Victor Borba"),
                List.of("Electronical", "Rock"), LocalDate.now(), "Bury_The_Light", 582);
        Track track2 = new Track(2, "Devil Trigger", List.of("Casey Edwards", "Ali Edwards"),
                List.of("Electronical", "Rock"), LocalDate.now(), "Devil_Trigger", 400);
        Track track3 = new Track(3, "The Vengeful One", List.of("Disturbed"),
                List.of("Hard Rock"), LocalDate.now(), "The_Vengeful_One", 252);
        Track track4 = new Track(4, "Bury the light", List.of("Casey Edwards", "Victor Borba"),
                List.of("Electronical", "Rock"), LocalDate.now(), "Bury_The_Light", 582);

        Spotify spotify = new SpotifyImpl();
        spotify.addPlaylist(playlist1);
        spotify.addTrackToPlaylist(playlist1, track2);
        spotify.addTrackToPlaylist(playlist1, track3);
        spotify.addTrackToPlaylist(playlist1, 0, track1);
        spotify.addTrackToPlaylist(playlist1, track4);
        spotify.addPlaylist(playlist2);
        spotify.addTrackToPlaylist(playlist2, track2);
        spotify.addTrackToPlaylist(playlist2, track3);

        List<Track> tracksPlaylist1 = spotify.getTracks(playlist1);
        System.out.println("Tamaño: " + tracksPlaylist1.size() + " canciones");
        // tracksPlaylist1.clear();
        tracksPlaylist1.forEach(System.out::println);
        String genero = "Electronical";
        List<Playlist> playlistsByGenre = spotify.findByGenre(genero);
        System.out.println("Listas que tienen el género  " + genero + ":");
        playlistsByGenre.forEach(System.out::println);
        List<String> topArtist = spotify.getTopArtists(playlist1);
        System.out.println("Los / Las artistas con más canciones en la lista es: ");
        topArtist.forEach(System.out::println);

        Track longestTrack = spotify.findLongestTrack(playlist1);
        System.out.println("La canción con mayor duración es: " + longestTrack);

        Track shortestTrack = spotify.findShortestTrack(playlist1);
        System.out.println("La canción con menor duración es: " + shortestTrack);

        Set<String> genres = spotify.getGenres(playlist1);
        System.out.println("El género / Los géneros de la lista " + playlist1 + " son:");
        genres.forEach(System.out::println);

        SortedSet<String> sortedGenres = spotify.getSortedGenres(playlist1);
        System.out.println("El género / Los géneros de la lista " + playlist1 + " son:");
        sortedGenres.forEach(System.out::println);

        LocalDate date1 = LocalDate.of(2000, 1, 1);
        LocalDate date2 = LocalDate.of(2050, 12, 30);
        List<Playlist> playlistByDates = spotify.findByDates(date1, date2);
        System.out.println("Listas que tengan canciones entre: " +
                date1.getDayOfMonth() + "-" + date1.getMonthValue() + "-" + date1.getYear() + " y " +
                date2.getDayOfMonth() + "-" + date2.getMonthValue() + "-" + date2.getYear());
        playlistByDates.forEach(System.out::println);
    }
}
