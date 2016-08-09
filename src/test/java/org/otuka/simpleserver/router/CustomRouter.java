package org.otuka.simpleserver.router;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.otuka.simpleserver.RequestWrapper;

/**
 * @author Anderson Otuka (anderson.otuka@dextra-sw.com)
 */
public class CustomRouter extends BaseRouter {

    @Override
    public boolean receive(RequestWrapper r) {
        final List<String> path = r.getPath();
        try {
            final HttpServletResponse resp = r.getResp();
            if (path.isEmpty() || path.size() != 1 || !path.get(0).equals("custom")) {
                resp.setStatus(400);
            }
            resp.getWriter().write("OK!");
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
