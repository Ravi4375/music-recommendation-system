package com.spotifyclone.service;

import com.spotifyclone.model.RecommendationResponse;
import com.spotifyclone.model.Track;
import com.spotifyclone.model.UserProfile;
import com.spotifyclone.repository.InMemoryMusicRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class RecommendationService {
    private final InMemoryMusicRepository repository;

    public RecommendationService(InMemoryMusicRepository repository) {
        this.repository = repository;
    }

    public RecommendationResponse recommendedFor(String userId) {
        UserProfile user = repository.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown user"));

        List<Track> tracks = repository.allTracks().stream()
                .filter(track -> user.favoriteGenres().stream()
                        .map(g -> g.toLowerCase(Locale.ROOT))
                        .toList()
                        .contains(track.genre().toLowerCase(Locale.ROOT))
                        || user.followedArtists().stream().anyMatch(a -> a.equalsIgnoreCase(track.artist()))
                        || user.likedTrackIds().contains(track.id()))
                .sorted(Comparator.comparingLong(Track::plays).reversed())
                .limit(8)
                .toList();

        return new RecommendationResponse(userId, "genre+artist+likes-hybrid", tracks);
    }
}
