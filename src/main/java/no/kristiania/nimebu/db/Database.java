package no.kristiania.nimebu.db;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Database {
    public static HikariDataSource getDataSource() throws IOException {
        var properties = new Properties();
        try (var reader = new FileReader("application.properties")) {
            properties.load(reader);
        }

        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(properties.getProperty("jdbc.url", "jdbc:sqlserver://localhost;encrypt=true;trustServerCertificate=true"));
        dataSource.setUsername(properties.getProperty("jdbc.username", "sa"));
        dataSource.setPassword(properties.getProperty("jdbc.password"));
        dataSource.setConnectionTimeout(5000);
        Flyway.configure().dataSource(dataSource).load().migrate();

        return dataSource;
    }
}
