package no.kristiania.nimebu;

import no.kristiania.nimebu.db.Database;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class ShopServer {
    private static final Logger logger = LoggerFactory.getLogger(ShopServer.class);

    private final Server shopServer;

    public ShopServer(int port, DataSource dataSource) throws IOException {
        this.shopServer = new Server(port);

        var wContext = new WebAppContext();
        wContext.setContextPath("/");
        wContext.setBaseResource(Resource.newClassPathResource("/webapp"));

        Resource resources = Resource.newClassPathResource("/webapp");

        var sourceDirectory = getSourceDirectory(resources);

        if (sourceDirectory != null && sourceDirectory.isDirectory()) {
            wContext.setBaseResource(Resource.newResource(sourceDirectory));
            wContext.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        } else {
            wContext.setBaseResource(resources);
        }

        wContext.addServlet(new ServletHolder(new ServletContainer(new ShopResourceConfig(dataSource))), "/api/*");

        shopServer.setHandler(new HandlerList(wContext));
    }

    private File getSourceDirectory(Resource resources) throws IOException {
        if(resources.getFile() == null) return null;

        var sourceDir = new File(resources.getFile().getAbsoluteFile().toString()
                .replace('\\', '/')
                .replace("target/classes", "src/main/resources"));
        return sourceDir.exists() ? sourceDir : null;
    }


    public void start() throws Exception {
        shopServer.start();
    }

    public URL getURL() throws MalformedURLException {
        return shopServer.getURI().toURL();
    }

    public static void main(String[] args) throws Exception {
        int port = Optional.ofNullable(System.getenv("HTTP_PLATFORM_PORT"))
                        .map(Integer::parseInt)
                        .orElse(9090);
        var server = new ShopServer(port, Database.getDataSource());
        server.start();
        logger.warn("Server starting at {}", server.getURL());
    }
}
