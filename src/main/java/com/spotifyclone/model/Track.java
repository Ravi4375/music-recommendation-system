package com.spotifyclone.model;

import java.util.List;

public record Track(
        String id,
        String title,
        String artist,
        String album,
        String genre,
        int durationSeconds,
        long plays,
        List<String> moods
) {
}
