package org.otuka.simpleserver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.otuka.http.HTTP;
import org.otuka.http.HTTPResponse;

/**
 * @author Anderson Otuka (anderson.otuka@dextra-sw.com)
 */
public class SimpleServerTest extends BaseTest {

    @Test
    public void testRun() {
        server = new SimpleServer("0.0.0.0", 56789);
        port = server.bind();
        String baseUrl = "http://localhost:" + port + "/";
        HTTPResponse httpResponse = doGet(baseUrl);
        assertEquals(200, httpResponse.getCode());

        httpResponse = doGet(baseUrl + "file.txt");
        assertEquals(200, httpResponse.getCode());
        assertEquals("This is a text file!\n", httpResponse.bodyText());

        httpResponse = doGet(baseUrl + "urlnotfound");
        assertEquals(404, httpResponse.getCode());
    }

    private HTTPResponse doGet(String url) {
        HTTP http = HTTP.create();
        http.request().url(url);
        return http.execute().response();
    }
}
