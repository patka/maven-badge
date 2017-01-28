package org.cognitor.maven.dao;

import java.util.List;

/**
 * @author Patrick Kranz
 */
public class Response {
    private int numFound;
    private List<Doc> docs;

    public int getNumFound() {
        return numFound;
    }

    public Response setNumFound(int numFound) {
        this.numFound = numFound;
        return this;
    }

    public List<Doc> getDocs() {
        return docs;
    }

    public Response setDocs(List<Doc> docs) {
        this.docs = docs;
        return this;
    }
}

class Doc {
    private String v;

    public String getV() {
        return v;
    }

    public Doc setV(String v) {
        this.v = v;
        return this;
    }
}
