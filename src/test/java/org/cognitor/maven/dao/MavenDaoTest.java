package org.cognitor.maven.dao;

import org.cognitor.maven.config.MavenConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

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

    @Before
    public void before() {
        this.mavenConfiguration = new MavenConfiguration().setUrl("http://maven.sonar.org");
        this.mavenDao = new MavenDao(restTemplateMock, mavenConfiguration);
    }

    @Test
    @Ignore
    public void shouldReturnCorrectVersionWhenSearchResultGiven() {
        when(restTemplateMock.getForObject(anyString(), MavenResponse.class)).thenReturn(
                new MavenResponse().setResponse(
                        new Response().setDocs(asList(new Doc().setV("1.0.0"))).setNumFound(1)));

        assertThat(mavenDao.getLatestVersion("org.cognitor.maven", "maven-badge"), is(equalTo("1.0.0")));
    }

    @Test
    @Ignore
    public void shouldReturnNotFoundWhenNoVersionInformationWasFound() {
        when(restTemplateMock.getForObject(anyString(), MavenResponse.class)).thenReturn(
                new MavenResponse().setResponse(new Response().setNumFound(0)));

        assertThat(mavenDao.getLatestVersion("org.cognitor.maven", "maven-badge"), is(equalTo("Not Found")));
    }
}