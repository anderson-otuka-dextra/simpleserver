import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.otuka.simpleserver.SimpleServer;

/**
 * @author Anderson Otuka (anderson.otuka@dextra-sw.com)
 */
public class TestMain {

    private static final Logger LOG = LogManager.getLogger(TestMain.class);

    public static void main(String[] args) {
        LOG.info("Starting App!");
        try (SimpleServer server = new SimpleServer("0.0.0.0", 5432)) {
            LOG.info("Point your browser to http://localhost:" + server.bind() + "/");
            server.listen();
        }
    }
}
