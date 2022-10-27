package no.kristiania.nimebu.db;

import no.kristiania.nimebu.Product;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProductDao {

    private final DataSource dataSource;

    private Map<Long, Product> products = new HashMap<>();

    public ProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Product product) throws SQLException {

        try (var connection = dataSource.getConnection()) {
             var sql = "insert into products (name, brand) values (?, ?)";
             try (PreparedStatement stmt = connection.prepareStatement(sql)){
                 stmt.setString(1, product.getName());
                 stmt.setString(2, product.getBrand());

                 stmt.executeUpdate();
             }
        }

        product.setId((long) products.size());
        products.put(product.getId(), product);
    }

    public Product retrieve(Long id) {
        return products.get(id);
    }
}
