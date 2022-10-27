package no.kristiania.nimebu.db;

import no.kristiania.nimebu.Product;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DaoTest {

    private ProductDao dao;

    @BeforeEach
    void setup() throws SQLException {
        var dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:DaoTest;DB_CLOSE_DELAY=-1");

        try (var con = dataSource.getConnection()){
            var stmt = con.createStatement();
            stmt.executeUpdate("create table products (id serial primary key, brand varchar(64), name varchar(64))");
        }
        dao = new ProductDao(dataSource);
    }

    @Test
    void shouldRetrieveSavedProduct() throws SQLException {
        var product = sampleProduct();
        dao.save(product);
        assertThat(dao.retrieve(product.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

    private Product sampleProduct() {
        return new Product("testBrand", "testName");
    }
}
