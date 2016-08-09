package org.otuka.simpleserver.internal;

import static java.lang.String.format;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Anderson Otuka (anderson.otuka@dextra-sw.com)
 */
public class ErrorFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(ErrorFilter.class);

    @Override public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            LOG.error("handler error", e);
            handleError((HttpServletResponse) servletResponse, e);
        }
    }

    private void handleError(HttpServletResponse resp, Exception exp) {
        if (resp.isCommitted()) {
            LOG.error("Error cannot be handled to client because response is commited");
            return;
        }
        resp.reset();
        try {
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(404);
            resp.getWriter().write(format("<html><head><title>404</title></head><body>Oops, something went wrong :)<p>%s</p></body></html>", exp.getMessage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override public void destroy() {

    }
}
