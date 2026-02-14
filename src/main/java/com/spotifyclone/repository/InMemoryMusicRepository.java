package com.spotifyclone.repository;

import com.spotifyclone.model.Playlist;
import com.spotifyclone.model.Track;
import com.spotifyclone.model.UserProfile;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryMusicRepository {
    private final Map<String, Track> tracks = new LinkedHashMap<>();
    private final Map<String, Playlist> playlists = new LinkedHashMap<>();
    private final Map<String, UserProfile> users = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        seedTracks();
        seedUsers();
        seedPlaylists();
    }

    public List<Track> allTracks() {
        return new ArrayList<>(tracks.values());
    }

    public List<Track> searchTracks(String query) {
        String q = query.toLowerCase(Locale.ROOT);
        return tracks.values().stream()
                .filter(t -> t.title().toLowerCase(Locale.ROOT).contains(q)
                        || t.artist().toLowerCase(Locale.ROOT).contains(q)
                        || t.album().toLowerCase(Locale.ROOT).contains(q)
                        || t.genre().toLowerCase(Locale.ROOT).contains(q))
                .toList();
    }

    public List<Track> trendingTracks() {
        return tracks.values().stream()
                .sorted(Comparator.comparingLong(Track::plays).reversed())
                .limit(10)
                .toList();
    }

    public Optional<UserProfile> findUserByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.username().equalsIgnoreCase(username))
                .findFirst();
    }

    public Optional<UserProfile> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public List<Playlist> allPlaylists() {
        return new ArrayList<>(playlists.values());
    }

    public List<Playlist> playlistsByOwner(String owner) {
        return playlists.values().stream().filter(p -> p.owner().equalsIgnoreCase(owner)).toList();
    }

    public Playlist createPlaylist(Playlist playlist) {
        playlists.put(playlist.id(), playlist);
        return playlist;
    }

    public List<Track> tracksByIds(List<String> ids) {
        return ids.stream().map(tracks::get).filter(t -> t != null).collect(Collectors.toList());
    }

    private void seedTracks() {
        putTrack(new Track("t1", "Blinding Lights", "The Weeknd", "After Hours", "Pop", 200, 2400000000L, List.of("energetic", "night")));
        putTrack(new Track("t2", "Levitating", "Dua Lipa", "Future Nostalgia", "Pop", 203, 1500000000L, List.of("dance", "happy")));
        putTrack(new Track("t3", "As It Was", "Harry Styles", "Harry's House", "Pop", 167, 1200000000L, List.of("chill", "indie")));
        putTrack(new Track("t4", "Paint The Town Red", "Doja Cat", "Scarlet", "Hip-Hop", 231, 600000000L, List.of("confident", "party")));
        putTrack(new Track("t5", "Luther", "Kendrick Lamar", "GNX", "Hip-Hop", 245, 250000000L, List.of("focus", "lyrical")));
        putTrack(new Track("t6", "Houdini", "Dua Lipa", "Radical Optimism", "Dance", 185, 200000000L, List.of("dance", "workout")));
        putTrack(new Track("t7", "Unholy", "Sam Smith", "Gloria", "Pop", 156, 650000000L, List.of("dark", "club")));
        putTrack(new Track("t8", "Kill Bill", "SZA", "SOS", "R&B", 153, 900000000L, List.of("moody", "late-night")));
        putTrack(new Track("t9", "Cruel Summer", "Taylor Swift", "Lover", "Pop", 178, 700000000L, List.of("summer", "nostalgic")));
        putTrack(new Track("t10", "Ojitos Lindos", "Bad Bunny", "Un Verano Sin Ti", "Latin", 258, 500000000L, List.of("chill", "romantic")));
        putTrack(new Track("t11", "Water", "Tyla", "Tyla", "Afrobeats", 200, 450000000L, List.of("dance", "smooth")));
        putTrack(new Track("t12", "vampire", "Olivia Rodrigo", "GUTS", "Pop", 219, 400000000L, List.of("sad", "dramatic")));
    }

    private void seedUsers() {
        users.put("u1", new UserProfile("u1", "demo", "Demo Listener", "demo@spotifyclone.dev",
                List.of("Pop", "Hip-Hop", "R&B"), List.of("t1", "t5", "t8"), List.of("The Weeknd", "SZA")));
        users.put("u2", new UserProfile("u2", "chillvibes", "Chill Vibes", "chill@spotifyclone.dev",
                List.of("Latin", "Dance", "Afrobeats"), List.of("t6", "t10", "t11"), List.of("Bad Bunny", "Tyla")));
    }

    private void seedPlaylists() {
        playlists.put("p1", new Playlist("p1", "Today's Top Hits", "Biggest songs right now.", "spotify", List.of("t1", "t2", "t3", "t9", "t11"), false));
        playlists.put("p2", new Playlist("p2", "RapCaviar", "Hip-Hop heat", "spotify", List.of("t4", "t5"), false));
        playlists.put("p3", new Playlist("p3", "Chill Mix", "Relax and unwind", "demo", List.of("t3", "t8", "t10"), true));
    }

    private void putTrack(Track track) {
        tracks.put(track.id(), track);
    }
}
