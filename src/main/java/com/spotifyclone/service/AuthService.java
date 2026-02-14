package com.spotifyclone.service;

import com.spotifyclone.model.AuthRequest;
import com.spotifyclone.model.AuthResponse;
import com.spotifyclone.model.UserProfile;
import com.spotifyclone.repository.InMemoryMusicRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class AuthService {
    private final InMemoryMusicRepository repository;

    public AuthService(InMemoryMusicRepository repository) {
        this.repository = repository;
    }

    public AuthResponse login(AuthRequest request) {
        UserProfile user = repository.findUserByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("Unknown user"));
        String tokenRaw = user.id() + ":" + user.username() + ":spotify-clone-token";
        String token = Base64.getEncoder().encodeToString(tokenRaw.getBytes());
        return new AuthResponse(token, user);
    }
}
