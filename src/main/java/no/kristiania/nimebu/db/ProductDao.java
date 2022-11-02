package no.kristiania.nimebu.db;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    void save(Product product) throws SQLException;

    Product retrieve(Long id) throws SQLException;

     List<Product> retrieveAll() throws SQLException;
}
