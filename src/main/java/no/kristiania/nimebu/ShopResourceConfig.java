package no.kristiania.nimebu;

import no.kristiania.nimebu.db.ProductDao;
import no.kristiania.nimebu.db.jdbc.JdbcProductDao;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.sql.DataSource;

class ShopResourceConfig extends ResourceConfig {
    public ShopResourceConfig(DataSource dataSource) {
        super(ProductsEndpoint.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(JdbcProductDao.class).to(ProductDao.class);
                bindFactory(() -> dataSource).to(DataSource.class);
            }
        });
    }
}
