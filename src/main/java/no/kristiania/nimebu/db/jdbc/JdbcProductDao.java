package no.kristiania.nimebu.db.jdbc;

import jakarta.inject.Inject;
import no.kristiania.nimebu.db.Product;
import no.kristiania.nimebu.db.ProductDao;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JdbcProductDao extends AbstractJdbcDao implements ProductDao {

    private final DataSource dataSource;

    @Inject
    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Product product) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "insert into products (name, brand) values (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                stmt.setString(1, product.getName());
                stmt.setString(2, product.getBrand());

                stmt.executeUpdate();
                try (var generatedKeys = stmt.getGeneratedKeys()) {
                    generatedKeys.next();
                    product.setId( generatedKeys.getLong(1));
                }
            }
        }
    }

    @Override
    public Product retrieve(Long id) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            try (var stmt = connection.prepareStatement("select * from products where id = ?")) {
                stmt.setLong(1, id);
                return collectSingleResult(stmt, JdbcProductDao::readProduct);
            }
        }
    }

    @Override
    public List<Product> retrieveAll() throws SQLException {
        try (var connection = dataSource.getConnection()){
            try (var stmt = connection.prepareStatement("select * from products")){
                return  collectQueryResult(stmt, JdbcProductDao::readProduct);
            }
        }
    }

    private static Product readProduct(ResultSet rs) throws SQLException {
        var product = new Product();
        product.setId(rs.getLong("id"));
        product.setBrand(rs.getString("brand"));
        product.setName(rs.getString("name"));
        return product;
    }
}
