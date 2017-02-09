package org.cognitor.maven.dao;

import org.cognitor.maven.config.MavenConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Patrick Kranz
 */
@RunWith(MockitoJUnitRunner.class)
public class MavenDaoTest {
    @Mock
    private RestTemplate restTemplateMock;

    private MavenConfiguration mavenConfiguration;

    private MavenDao mavenDao;

    private URI expectedUri;

    @Before
    public void before() throws URISyntaxException {
        this.mavenConfiguration = new MavenConfiguration().setUrl("http://maven.sonar.org");
        this.mavenDao = new MavenDao(restTemplateMock, mavenConfiguration);
        expectedUri = new URI("http://maven.sonar.org/solrsearch/select?q=g:org.cognitor.maven+AND+a:maven-badge&rows=1&core=gav");
    }

    @Test
    public void shouldReturnCorrectVersionWhenSearchResultGiven() throws URISyntaxException {
        when(restTemplateMock.getForObject(expectedUri, MavenResponse.class)).thenReturn(
                new MavenResponse().setResponse(
                        new Response().setDocs(asList(new Doc().setV("1.0.0"))).setNumFound(1)));

        assertThat(mavenDao.getLatestVersion("org.cognitor.maven", "maven-badge"), is(equalTo("1.0.0")));
    }

    @Test
    public void shouldReturnNotFoundWhenNoVersionInformationWasFound() {
        when(restTemplateMock.getForObject(expectedUri, MavenResponse.class)).thenReturn(
                new MavenResponse().setResponse(new Response().setNumFound(0)));

        assertThat(mavenDao.getLatestVersion("org.cognitor.maven", "maven-badge"), is(equalTo("Not Found")));
    }
}