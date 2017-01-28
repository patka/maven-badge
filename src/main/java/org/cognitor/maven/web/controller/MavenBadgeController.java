package org.cognitor.maven.web.controller;

import org.cognitor.maven.dao.MavenDao;
import org.cognitor.maven.dao.ShieldDao;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.Enumeration;

import static java.lang.String.format;

/**
 * @author Patrick Kranz
 */
@Controller
public class MavenBadgeController {
    private final MavenDao mavenDao;
    private final ShieldDao shieldDao;

    public MavenBadgeController(MavenDao mavenDao, ShieldDao shieldDao) {
        this.mavenDao = mavenDao;
        this.shieldDao = shieldDao;
    }

    @RequestMapping("/maven/{groupId}/{artifactId}/badge.png")
    public ResponseEntity<byte[]> badgePng(@PathVariable("groupId") String groupId,
                                        @PathVariable("artifactId") String artifactId,
                                        HttpServletRequest request) throws URISyntaxException {
        MultiValueMap<String, String> headers = getHttpHeaders(request);
        String version = mavenDao.getLatestVersion(groupId, artifactId);
        byte[] image = shieldDao.getImage(version, headers);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
    }

    private MultiValueMap<String, String> getHttpHeaders(HttpServletRequest request) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            headers.add(header, request.getHeader(header));
        }
        return headers;
    }
}
