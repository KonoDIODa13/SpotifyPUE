package spotify.application;

import spotify.domain.Playlist;
import spotify.records.TopArtists;
import spotify.domain.Track;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


public class SpotifyImpl implements Spotify {
    private final Map<Playlist, List<Track>> map = new HashMap<>();

    @Override
    public boolean addPlaylist(Playlist playlist) {
        if (map.containsKey(playlist)) return false;
        map.put(playlist, new ArrayList<>());
        return true;
    }

    @Override
    public void addTrackToPlaylist(Playlist playlist, Track track) {
        if (!map.containsKey(playlist))
            throw new IllegalArgumentException("La playlist" + playlist.getId() + " no existe en Spotify.");
        if (map.get(playlist).contains(track))
            throw new IllegalArgumentException("La canción " + track.getTitle() + " ya existe en la lista.");
        map.get(playlist).add(track);
    }

    @Override
    public void addTrackToPlaylist(Playlist playlist, int position, Track track) {
        if (!map.containsKey(playlist))
            throw new IllegalArgumentException("La playlist" + playlist.getId() + " no existe en Spotify.");
        if (map.get(playlist).contains(track))
            throw new IllegalArgumentException("La canción " + track.getTitle() + " ya existe en la lista.");
        if (position < 0 || position >= map.get(playlist).size())
            throw new ArrayIndexOutOfBoundsException("El indice no puede tener ese valor");
        map.get(playlist).add(position, track);

    }

    @Override
    public List<Track> getTracks(Playlist playlist) {
        if (!map.containsKey(playlist))
            throw new IllegalArgumentException("La playlist" + playlist.getId() + " no existe en Spotify.");
        return List.copyOf(map.get(playlist));
    }

    @Override
    public List<Playlist> findByGenre(String genre) {
        return map.entrySet()
                .stream()
                .filter(entry ->
                        entry.getValue()
                                .stream()
                                .anyMatch(track -> track.getGenres().contains(genre)))
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public List<Playlist> findByArtist(String artist) {
        return map.entrySet()
                .stream()
                .filter(entry ->
                        entry.getValue()
                                .stream()
                                .anyMatch(track -> track.getArtists().contains(artist)))
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public Track findLongestTrack(Playlist playlist) {
        if (!map.containsKey(playlist))
            throw new IllegalArgumentException("La playlist" + playlist.getId() + " no existe en Spotify.");
        return map.get(playlist).stream()
                .max(Comparator.comparing(Track::getSeconds))
                .orElse(null);
    }

    @Override
    public Track findShortestTrack(Playlist playlist) {
        if (!map.containsKey(playlist))
            throw new IllegalArgumentException("La playlist" + playlist.getId() + " no existe en Spotify.");
        return map.get(playlist).stream()
                .min(Comparator.comparing(Track::getSeconds))
                .orElse(null);
    }

    @Override
    public Double getAverageDuration(Playlist playlist) {
        if (!map.containsKey(playlist))
            throw new IllegalArgumentException("La playlist" + playlist.getId() + " no existe en Spotify.");
        return map.get(playlist).stream()
                .mapToDouble(Track::getSeconds).average()
                .orElse(0);
    }

    @Override
    public Set<String> getGenres(Playlist playlist) {
        if (!map.containsKey(playlist))
            throw new IllegalArgumentException("La playlist" + playlist.getId() + " no existe en Spotify.");
        return getTracks(playlist)
                .stream()
                .flatMap(track -> track.getGenres().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public SortedSet<String> getSortedGenres(Playlist playlist) {
        if (!map.containsKey(playlist))
            throw new IllegalArgumentException("La playlist" + playlist.getId() + " no existe en Spotify.");
        return new TreeSet<>(getGenres(playlist));
    }

    @Override
    public List<TopArtists> getTopArtists(Playlist playlist) {
        if (!map.containsKey(playlist))
            throw new IllegalArgumentException("La playlist" + playlist.getId() + " no existe en Spotify.");
        /*
        return getTracks(playlist)
                .stream()
                .flatMap(track -> track.getArtists().stream())
                .collect(groupingBy(artist -> artist, counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                //.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()) es lo mismo que lo de arriba
                .map(Map.Entry::getKey)
                .limit(5)
                .collect(Collectors.toList()):
         */
        return getTracks(playlist)
                .stream()
                .flatMap(track -> track.getArtists().stream())
                .collect(groupingBy(artist -> artist, counting()))
                .entrySet()
                .stream()
                .map(entry -> new TopArtists(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(TopArtists::ocurrence).reversed())
                .limit(5)
                .toList();
    }

    @Override
    public List<Playlist> findByDates(LocalDate start, LocalDate end) {
        return map.entrySet()
                .stream()
                .filter(playList -> playList.getValue()
                        .stream()
                        .anyMatch(track -> (track.getReleaseDate().isAfter(start) && track.getReleaseDate().isBefore(end))))
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public List<Playlist> findByDatesByTrackDate(LocalDate start, LocalDate end, String artista) {
        return map.entrySet()
                .stream()
                .filter(playList -> playList.getValue()
                        .stream()
                        .anyMatch(track -> (track.getReleaseDate().isAfter(start) && track.getReleaseDate().isBefore(end) && track.getArtists().contains(artista))))
                .map(Map.Entry::getKey)
                .toList();
    }

    private List<String> getArtist(Playlist playlist) {
        return getTracks(playlist)
                .stream()
                .flatMap(track -> track.getArtists().stream())
                .collect(Collectors.toList());
    }
}
