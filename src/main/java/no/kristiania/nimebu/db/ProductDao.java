package no.kristiania.nimebu.db;

import no.kristiania.nimebu.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductDao {

    private Map<Long, Product> products = new HashMap<>();

    public void save(Product product) {
        product.setId((long) (products.size() +1));
        products.put(product.getId(), product);
    }

    public Product retrieve(Long id) {
        return products.get(id);
    }


}
