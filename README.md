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

## Run in IntelliJ (fix for `org.springframework.web.bind.annotation does not exist`)
If IntelliJ shows this error:
`package org.springframework.web.bind.annotation does not exist`
it means Spring dependencies were not imported into the IDE classpath.

Do this:
1. Install/use **JDK 17** in IntelliJ (`File > Project Structure > Project SDK`).
2. Open the project from the **`pom.xml`** root folder.
3. In Maven tool window, click **Reload All Maven Projects**.
4. Ensure IntelliJ uses bundled Maven or a valid local Maven (`Settings > Build Tools > Maven`).
5. Run from IntelliJ using:
   - Main class: `com.spotifyclone.MusicRecommendationApplication`
   - Or Maven goal: `spring-boot:run`

### If dependencies still do not resolve
Run in terminal from project root:
```bash
mvn -U clean compile
```
Then reload Maven in IntelliJ again.

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
