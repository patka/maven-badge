package org.cognitor.maven.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Patrick Kranz
 */
@ConfigurationProperties("maven")
@Configuration
public class MavenConfiguration {
    private String url;

    public String getUrl() {
        return url;
    }

    public MavenConfiguration setUrl(String url) {
        this.url = url;
        return this;
    }
}
