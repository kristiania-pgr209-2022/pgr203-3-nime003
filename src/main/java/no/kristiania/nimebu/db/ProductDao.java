package no.kristiania.nimebu.db;

import no.kristiania.nimebu.Product;

import javax.sql.DataSource;
import java.sql.*;

public class ProductDao {

    private final DataSource dataSource;

    public ProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Product product) throws SQLException {

        try (var connection = dataSource.getConnection()) {
             var sql = "insert into products (name, brand) values (?, ?)";
             try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                 stmt.setString(1, product.getName());
                 stmt.setString(2, product.getBrand());

                 stmt.executeUpdate();
                 try (var generatedKeys = stmt.getGeneratedKeys()) {
                     generatedKeys.next();
                     product.setId(generatedKeys.getLong("id"));
                 }
             }
        }
    }

    public Product retrieve(Long id) throws SQLException {

        try (var connection = dataSource.getConnection()) {
            try (var stmt = connection.prepareStatement("select * from products where id = ?")) {
                stmt.setLong(1, id);
                try (var rs = stmt.executeQuery()) {
                    rs.next();
                    Product product = new Product("test", "name");
                    product.setId(rs.getLong("id"));
                    return product;
                }
            }

        }
    }
}
