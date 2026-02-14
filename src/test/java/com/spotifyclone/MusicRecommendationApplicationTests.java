package com.spotifyclone;

import com.spotifyclone.model.RecommendationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MusicRecommendationApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void recommendationEndpointReturnsTracks() {
        RecommendationResponse response = restTemplate.getForObject("/api/recommendations/u1", RecommendationResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.tracks()).isNotEmpty();
    }
}
