package com.spotifyclone.model;

import java.util.List;

public record Playlist(
        String id,
        String name,
        String description,
        String owner,
        List<String> trackIds,
        boolean collaborative
) {
}
