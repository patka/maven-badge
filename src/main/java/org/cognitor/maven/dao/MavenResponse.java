package org.cognitor.maven.dao;

/**
 * @author Patrick Kranz
 */
public class MavenResponse {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public MavenResponse setResponse(Response response) {
        this.response = response;
        return this;
    }
}
