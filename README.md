# Music Recommendation System (Spotify-Inspired)

A Java full-stack music recommendation system inspired by Spotify UX patterns.

## Features included
- Spring Boot REST API for:
  - Demo auth/login
  - Trending tracks feed
  - Search by track/artist/album/genre
  - Playlist listing and creation
  - Personalized recommendations per user profile
- Spotify-like single-page UI (served from Spring static resources) with:
  - Left sidebar navigation
  - Home/trending feed
  - Search experience
  - Your library playlist section
  - "Made For You" recommendations
  - Bottom now-playing bar

## Stack
- Java 17
- Spring Boot 3
- Maven
- Vanilla HTML/CSS/JS frontend

## Run locally
```bash
mvn spring-boot:run
```
Then open: http://localhost:8080

## Demo login
- Username: `demo`
- Password: `demo`

## API examples
```bash
curl http://localhost:8080/api/browse/trending
curl "http://localhost:8080/api/tracks?q=dua"
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"username":"demo","password":"demo"}'
```

## Important note
This project is a Spotify-inspired educational clone and **not an exact reproduction** of Spotify's proprietary product, backend, licensing, rights management, recommendation ML stack, or streaming infrastructure.
