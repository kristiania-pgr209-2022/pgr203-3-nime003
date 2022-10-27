package no.kristiania.nimebu;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.servlet.http.HttpServlet;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Path("/products")
public class ProductsEndpoint extends HttpServlet {
    private static final List<Product> products =new ArrayList<>();
    static {
        products.add(new Product("testBrand", "testProduct"));
    }

    @Path("/")
    @GET
    public Response getProducts() {
        JsonArrayBuilder result = Json.createArrayBuilder();
        for (Product product : products) {
            result.add(Json.createObjectBuilder()
                    .add("productBrand", product.getProductBrand())
                    .add("productName", product.getName())
            );
        }

        return Response.ok(result.build().toString()).build();
    }

    @Path("/")
    @POST
    public Response addProduct(String body) {
        var jsonProduct = Json.createReader(new StringReader(body)).readObject();
        var product =new Product(
                jsonProduct.getString("productBrand"),
                jsonProduct.getString("productName")
        );
        products.add(product);

        return Response.ok().build();
    }
}
