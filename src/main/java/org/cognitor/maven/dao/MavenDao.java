package org.cognitor.maven.dao;

import org.cognitor.maven.config.MavenConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;

import java.net.URI;

import static java.lang.String.format;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

/**
 * @author Patrick Kranz
 */
@Component
public class MavenDao {
    private static final String NOT_FOUND_ANSWER = "Not Found";
    private final RestTemplate restTemplate;
    private final MavenConfiguration mavenConfiguration;

    @Autowired
    public MavenDao(RestTemplate restTemplate, MavenConfiguration configuration) {
        this.restTemplate = restTemplate;
        this.mavenConfiguration = configuration;
    }

    public String getLatestVersion(String groupId, String artifactId) {
        MavenResponse mavenResponse = restTemplate.getForObject(buildUrl(groupId, artifactId), MavenResponse.class);
        if (mavenResponse.getResponse().getNumFound() == 0) {
            return NOT_FOUND_ANSWER;
        }
        return mavenResponse.getResponse().getDocs().get(0).getV();
    }

    private URI buildUrl(String groupId, String artifactId) {
        UriComponents queryUrl = fromHttpUrl(mavenConfiguration.getUrl())
                .path("solrsearch/select")
                .queryParam("q", format("g:%s+AND+a:%s", groupId, artifactId))
                .queryParam("rows", 1)
                .queryParam("core", "gav").build(false);

        return queryUrl.toUri();
    }
}
