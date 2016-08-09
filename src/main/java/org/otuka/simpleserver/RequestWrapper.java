package org.otuka.simpleserver;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Anderson Otuka (anderson.otuka@dextra-sw.com)
 */
public class RequestWrapper {

    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final List<String> path;
    private final String uri;

    public RequestWrapper(HttpServletRequest req, HttpServletResponse resp, List<String> path, String uri) {
        this.req = req;
        this.resp = resp;
        this.path = path;
        this.uri = uri;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public HttpServletResponse getResp() {
        return resp;
    }

    public List<String> getPath() {
        return path;
    }

    public String getUri() {
        return uri;
    }
}
