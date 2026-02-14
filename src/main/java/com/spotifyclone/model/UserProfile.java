package com.spotifyclone.model;

import java.util.List;

public record UserProfile(
        String id,
        String username,
        String displayName,
        String email,
        List<String> favoriteGenres,
        List<String> likedTrackIds,
        List<String> followedArtists
) {
}
