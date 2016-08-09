package org.otuka.simpleserver;

import org.junit.After;
import org.junit.Before;

/**
 * @author Anderson Otuka (anderson.otuka@dextra-sw.com)
 */
public abstract class BaseTest {

    protected SimpleServer server;
    protected int port;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        server.close();
    }
}
