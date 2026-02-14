package com.spotifyclone.model;

import java.util.List;

public record RecommendationResponse(
        String userId,
        String strategy,
        List<Track> tracks
) {
}
