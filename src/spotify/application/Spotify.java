package spotify.application;


import spotify.domain.Playlist;
import spotify.records.TopArtists;
import spotify.domain.Track;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

public interface Spotify {
    boolean addPlaylist (Playlist playlist);
    void addTrackToPlaylist(Playlist playlist, Track track);
    void addTrackToPlaylist(Playlist playlist, int position, Track track);
    List<Track> getTracks(Playlist playlist);
    List<Playlist> findByGenre(String genre);
    List<Playlist> findByArtist(String artist);
    Track findLongestTrack(Playlist playlist);
    Track findShortestTrack(Playlist playlist);
    Double getAverageDuration(Playlist playlist);
    Set<String> getGenres (Playlist playlist);
    SortedSet<String> getSortedGenres(Playlist playlist);
    List<TopArtists> getTopArtists(Playlist playlist); //artistas mas repetidos en las playlists
    List<Playlist> findByDates(LocalDate start, LocalDate end);
    List<Playlist> findByDatesByTrackDate(LocalDate start, LocalDate end, String artista);// devuelve las playlist que tengan canciones con ese artista


}
