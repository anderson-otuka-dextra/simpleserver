import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.otuka.simpleserver.SimpleServer;
import org.otuka.simpleserver.router.CustomRouter;

/**
 * @author Anderson Otuka (anderson.otuka@dextra-sw.com)
 */
public class TestCustomRouterMain {

    private static final Logger LOG = LogManager.getLogger(TestCustomRouterMain.class);

    public static void main(String[] args) {
        LOG.info("Starting App with Custom Router!");
        try (SimpleServer server = new SimpleServer<>("0.0.0.0", 5432, CustomRouter.class)) {
            LOG.info("Point your browser to http://localhost:" + server.bind() + "/custom");
            server.listen();
        }
    }
}
