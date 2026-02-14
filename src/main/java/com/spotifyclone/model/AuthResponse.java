package com.spotifyclone.model;

public record AuthResponse(
        String token,
        UserProfile user
) {
}
