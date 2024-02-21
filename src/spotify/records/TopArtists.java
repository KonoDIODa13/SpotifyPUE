package spotify.records;

public record TopArtists(String artist, long ocurrence) {
    @Override
    public String toString() {
        return "Artista: '" + artist + '\'' +
                ", Ocurrencias " + ocurrence +
                '.';
    }
}
