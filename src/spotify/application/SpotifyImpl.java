package spotify.application;

import spotify.domain.Playlist;
import spotify.domain.Track;

import java.time.LocalDate;
import java.util.*;


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
        return map.get(playlist).stream().max(Comparator.comparing(Track::getSeconds)).orElse(null);
    }

    @Override
    public Track findShortestTrack(Playlist playlist) {
        return map.get(playlist).stream().min(Comparator.comparing(Track::getSeconds)).orElse(null);
    }

    @Override
    public Double getAverageDuration(Playlist playlist) {
        double totalTrackDuration = 0;
        for (Track track : map.get(playlist)) {
            totalTrackDuration += track.getSeconds();
        }
        return totalTrackDuration / map.get(playlist).size();
    }

    @Override
    public Set<String> getGenres(Playlist playlist) {
        Set<String> genres = new HashSet<>();
        for (Track track : map.get(playlist)) {
            genres.addAll(track.getGenres());
        }
        return genres;
    }

    @Override
    public SortedSet<String> getSortedGenres(Playlist playlist) {
        SortedSet<String> genres = new TreeSet<>();
        for (Track track : getTracks(playlist)) {
            genres.addAll(track.getGenres());
        }
        return genres;
    }

    @Override
    public List<String> getTopArtists(Playlist playlist) {
        if (!map.containsKey(playlist))
            throw new IllegalArgumentException("La playlist" + playlist.getId() + " no existe en Spotify.");
        Map<String, Integer> artistsAndNum = new HashMap<>();
        int contMax = 1;
        for (Track track : map.get(playlist)) {
            for (String artist : track.getArtists()) {
                int contArtist = 1;
                if (!artistsAndNum.containsKey(artist)) {
                    artistsAndNum.putIfAbsent(artist, contArtist);
                } else {
                    contArtist += artistsAndNum.get(artist);
                    artistsAndNum.replace(artist, contArtist);
                    if (contMax < contArtist) {
                        contMax = contArtist;
                    }
                }
            }
        }
        //System.out.println(artistsAndNum.entrySet());
        List<String> topArtist = new ArrayList<>();
        for (String artist : artistsAndNum.keySet()) {
            if (artistsAndNum.get(artist) == contMax) {
                topArtist.add(artist);
            }
        }
        return topArtist;
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
}
