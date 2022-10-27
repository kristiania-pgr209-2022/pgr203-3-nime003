package no.kristiania.nimebu.dbTest;

import no.kristiania.nimebu.Product;
import no.kristiania.nimebu.db.ProductDao;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class dbTest {
    private ProductDao dao;

    @Test
    void shouldRetrieveSavedProduct() {
        var product = sampleProduct();
        dao.save(product);
        assertThat(dao.retrieve(product.getId()))
                .hasNoNullFieldsOrProperties()
                .isNotSameAs(product)
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

    private Product sampleProduct() {
        return new Product("", "");
    }
}
