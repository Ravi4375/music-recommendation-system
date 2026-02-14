package com.spotifyclone.controller;

import com.spotifyclone.model.Playlist;
import com.spotifyclone.model.RecommendationResponse;
import com.spotifyclone.model.Track;
import com.spotifyclone.model.UserProfile;
import com.spotifyclone.repository.InMemoryMusicRepository;
import com.spotifyclone.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class MusicController {
    private final InMemoryMusicRepository repository;
    private final RecommendationService recommendationService;

    public MusicController(InMemoryMusicRepository repository, RecommendationService recommendationService) {
        this.repository = repository;
        this.recommendationService = recommendationService;
    }

    @GetMapping("/tracks")
    public List<Track> tracks(@RequestParam(required = false) String q) {
        if (q == null || q.isBlank()) {
            return repository.allTracks();
        }
        return repository.searchTracks(q);
    }

    @GetMapping("/browse/trending")
    public List<Track> trending() {
        return repository.trendingTracks();
    }

    @GetMapping("/playlists")
    public List<Playlist> playlists(@RequestParam(required = false) String owner) {
        if (owner == null || owner.isBlank()) {
            return repository.allPlaylists();
        }
        return repository.playlistsByOwner(owner);
    }

    @PostMapping("/playlists")
    public Playlist createPlaylist(@RequestBody Map<String, Object> payload) {
        String name = payload.getOrDefault("name", "My Playlist").toString();
        String description = payload.getOrDefault("description", "Custom playlist").toString();
        String owner = payload.getOrDefault("owner", "demo").toString();
        boolean collaborative = Boolean.parseBoolean(payload.getOrDefault("collaborative", false).toString());

        @SuppressWarnings("unchecked")
        List<String> trackIds = (List<String>) payload.getOrDefault("trackIds", List.of());

        Playlist playlist = new Playlist(UUID.randomUUID().toString(), name, description, owner, trackIds, collaborative);
        return repository.createPlaylist(playlist);
    }

    @GetMapping("/users/{userId}")
    public UserProfile user(@PathVariable String userId) {
        return repository.findUserById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @GetMapping("/recommendations/{userId}")
    public RecommendationResponse recommendations(@PathVariable String userId) {
        return recommendationService.recommendedFor(userId);
    }
}
