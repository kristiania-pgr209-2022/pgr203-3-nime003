package no.kristiania.nimebu.db;

import no.kristiania.nimebu.Product;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class DaoTest {
    private ProductDao dao = new ProductDao();

    @Test
    void shouldRetrieveSavedProduct() {
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
