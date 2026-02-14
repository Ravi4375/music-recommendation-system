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
it means Maven dependencies are not on the IDE classpath yet.

Do this exactly:
1. Install/use **JDK 17** in IntelliJ (`File > Project Structure > Project SDK`).
2. Open the project from the folder containing **`pom.xml`**.
3. Enable Maven import and click **Reload All Maven Projects** (Maven tool window).
4. Ensure Maven is not in offline mode (`Settings > Build Tools > Maven > Offline` unchecked).
5. Set build/run to Maven or IntelliJ with Maven classpath:
   - `Settings > Build, Execution, Deployment > Build Tools > Maven > Runner`
   - Check **Delegate IDE build/run actions to Maven**.
6. Run `com.spotifyclone.MusicRecommendationApplication`.

### If error still appears
Run these from terminal (project root), then reload Maven:
```bash
mvn -U dependency:purge-local-repository
mvn -U clean compile
```

### If your network blocks Maven Central
This project includes fallback repositories in `pom.xml` (Aliyun and Spring release repos). If your company network blocks all external repositories, configure your proxy in Maven `settings.xml`.

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
