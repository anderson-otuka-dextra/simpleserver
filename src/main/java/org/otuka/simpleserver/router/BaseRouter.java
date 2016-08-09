package org.otuka.simpleserver.router;

import static java.lang.String.format;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.otuka.simpleserver.RequestWrapper;

/**
 * @author Anderson Otuka (anderson.otuka@dextra-sw.com)
 */
public abstract class BaseRouter extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(BaseRouter.class);
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        final List<String> path = getPath(req);
        final String uri = getURI(req);
        LOG.info(String.format("doGet: %s", uri));
        if (receive(new RequestWrapper(req, resp, path, uri))) {
            return;
        }
        try {
            resp.sendError(404, format("The requested URI could not be found: %s%n", uri));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract boolean receive(RequestWrapper r);

    private List<String> getPath(HttpServletRequest req) {
        final String uri = getURI(req);
        if (uri.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(uri.substring(1).split("/"));
    }

    private String getURI(HttpServletRequest req) {
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        if (contextPath != null && !contextPath.isEmpty()) {
            uri = uri.replace(contextPath, "");
        }
        return uri;
    }
}
