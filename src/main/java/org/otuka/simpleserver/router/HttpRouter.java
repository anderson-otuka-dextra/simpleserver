package org.otuka.simpleserver.router;

import org.otuka.simpleserver.handler.ResourceFileHandler;
import org.otuka.simpleserver.RequestWrapper;

/**
 * @author Anderson Otuka (anderson.otuka@dextra-sw.com)
 */
public class HttpRouter extends BaseRouter {

    public boolean receive(RequestWrapper r) {
        return new ResourceFileHandler(r.getUri()).parse(r.getResp());
    }

}
