package org.otuka.simpleserver;

import java.io.Closeable;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.server.Server;
import org.otuka.simpleserver.internal.ErrorFilter;
import org.otuka.simpleserver.router.BaseRouter;
import org.otuka.simpleserver.router.HttpRouter;

public class SimpleServer<T extends BaseRouter> implements Closeable {

	private static final Logger LOG = LogManager.getLogger(SimpleServer.class);

	private final String host;
	private final int port;
	private Server server;

	public SimpleServer(String host, int port) {
		this(host, port, (Class<T>) HttpRouter.class);
	}

	public SimpleServer(String host, int port, Class<T> baseServlet) {
		LOG.info("Creating Server...");
		this.host = host;
		this.port = port;
		this.server = new Server();
		try {
			configureJetty(baseServlet);
			server.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void configureJetty(Class<T> requestHandler) {
		ServletHandler handler = new ServletHandler();
		handler.addFilterWithMapping(ErrorFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
		handler.addServletWithMapping(requestHandler, "/*");
		server.setHandler(handler);
	}

	public void close() {
		if (server != null) {
			try {
				server.stop();
			} catch (Exception e) {
				LOG.info("Error shutting down", e);
			}
		}
	}

	public boolean run() {
		LOG.info("Starting server.");
		bind();
		return listen();
	}

	public int bind() {
		LOG.info("Binding host {} and port {}", host, port);
		try {
			ServerConnector connector = new ServerConnector(server);
			connector.setHost(host);
			connector.setPort(port);
			server.addConnector(connector);
			connector.start();
			return connector.getLocalPort();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean listen() {
		LOG.info("Server listening.");
		try {
			server.join();
		} catch (InterruptedException e) {
			LOG.info("Server interrupted.");
			return false;
		}
		LOG.info("Server shutting down.");
		return true;
	}
}
