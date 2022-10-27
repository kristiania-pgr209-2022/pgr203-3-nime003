package no.kristiania.nimebu.db;

import no.kristiania.nimebu.Product;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.sql.DataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DaoTest {

    private ProductDao dao;

    @BeforeEach
    void setup(){
        var dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:DaoTest");

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
