package no.kristiania.nimebu.db;

import no.kristiania.nimebu.db.jdbc.JdbcProductDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DaoTest {

    private ProductDao dao;

    @BeforeEach
    void setup() {
        dao = new JdbcProductDao(InMemoryDatasource.createTestDataSource());
    }

    @Test
    void shouldRetrieveSavedProduct() throws SQLException {
        var product = sampleProduct();
        dao.save(product);
        assertThat(dao.retrieve(product.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(product)
                .isNotSameAs(product);

    }

    private Product sampleProduct() {
        var product = new Product();
        product.setBrand("test");
        product.setName("name");
        return product;
    }
}
