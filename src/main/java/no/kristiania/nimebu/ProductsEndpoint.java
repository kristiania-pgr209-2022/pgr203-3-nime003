package no.kristiania.nimebu;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import no.kristiania.nimebu.db.Product;
import no.kristiania.nimebu.db.ProductDao;

import java.io.StringReader;
import java.sql.SQLException;

@Path("/products")
public class ProductsEndpoint {

    @Inject
    private ProductDao productDao;

    @GET
    public Response getProducts() throws SQLException {
        var products = productDao.retrieveAll();

        JsonArrayBuilder result = Json.createArrayBuilder();
        for (Product product : products) {
            result.add(Json.createObjectBuilder()
                    .add("productBrand", product.getBrand())
                    .add("productName", product.getName())
            );
        }

        return Response.ok(result.build().toString()).build();
    }

    @POST
    public Response addProduct(String body) throws SQLException {
        var jsonProduct = Json.createReader(new StringReader(body)).readObject();

        var product = new Product();
        product.setBrand(jsonProduct.getString("productBrand"));
        product.setName(jsonProduct.getString("productName"));

        productDao.save(product);
        return Response.ok().build();
    }
}
