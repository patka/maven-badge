package org.cognitor.maven.dao;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.GET;

/**
 * @author Patrick Kranz
 */
@Component
public class ShieldDao {
    private static final String SHIELDS_URL_TEMPLATE = "https://img.shields.io/badge/maven-%s-%s.png";
    private static final String BLUE = "blue";
    private final Map<String, String> replacements;
    private final RestTemplate restTemplate;

    public ShieldDao(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        replacements = new HashMap<>();
        replacements.put("_", "__");
        replacements.put(" ", "_");
        replacements.put("-", "--");
    }

    public byte[] getImage(String version, MultiValueMap<String, String> headers) throws URISyntaxException {
        String url = format(SHIELDS_URL_TEMPLATE, replace(version), BLUE);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, GET,
                new RequestEntity<byte[]>(headers, GET, new URI(url)), byte[].class);
        return response.getBody();
    }

    private String replace(String original) {
        for (String key : replacements.keySet()) {
            original = original.replaceAll(key, replacements.get(key));
        }
        return original;
    }
}
