package org.otuka.simpleserver.router;

import static org.junit.Assert.*;

import org.junit.Test;
import org.otuka.http.HTTP;
import org.otuka.http.HTTPResponse;
import org.otuka.simpleserver.BaseTest;
import org.otuka.simpleserver.SimpleServer;

/**
 * @author Anderson Otuka (anderson.otuka@dextra-sw.com)
 */
public class CustomRouterTest extends BaseTest {

    @Test
    public void testRun() {
        server = new SimpleServer<>("0.0.0.0", 56789, CustomRouter.class);
        port = server.bind();
        String baseUrl = "http://localhost:" + port + "/";
        HTTPResponse httpResponse = doGet(baseUrl);
        assertEquals(400, httpResponse.getCode());

        httpResponse = doGet(baseUrl + "custom");
        assertEquals(200, httpResponse.getCode());
        assertEquals("OK!", httpResponse.bodyText());
    }

    private HTTPResponse doGet(String url) {
        HTTP http = HTTP.create();
        http.request().url(url);
        return http.execute().response();
    }

}
