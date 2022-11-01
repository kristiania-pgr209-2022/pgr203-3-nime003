package no.kristiania.nimebu;

import jakarta.json.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class ShopServerTest {
    public static ShopServer server;
    @BeforeEach
    void setup() throws Exception {
        server = new ShopServer(0);
        server.start();
    }

    @Test
    void httpResponseShouldBe200Test() throws IOException {
        var connection = openConnection("/");
        assertThat(connection.getResponseCode()).isEqualTo(200);
    }


    @Test
    public void shouldServeCorrectTitle() throws IOException {
        var connection = openConnection("/");
        assertThat(connection.getInputStream()).asString(StandardCharsets.UTF_8).contains("<title>Nimebu Shop</title>");
    }

    @Test
    void getAllProductsTest() throws IOException {
        var connection = openConnection("/api/products");

        assertThat(connection.getResponseCode()).isEqualTo(200);
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("{\"productBrand\":\"testBrand\"");
    }
    @Test
    void shouldAddProductsTest() throws IOException {
        var postConnection = openConnection("/api/products");
        postConnection.setRequestMethod("POST");
        postConnection.setDoOutput(true);
        postConnection.getOutputStream().write(
                Json.createObjectBuilder()
                .add("productBrand","Doors")
                .add("productName", "Doors OS 15")
                        .build()
                        .toString()
                        .getBytes(StandardCharsets.UTF_8)
        );
        assertThat(postConnection.getResponseCode()).as(postConnection.getResponseMessage()
        + " for " + postConnection.getURL()).isEqualTo(200);

        var connection = openConnection("/api/products");
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("{\"productBrand\":\"Doors\"");
    }


    private HttpURLConnection openConnection(String spec) throws IOException {
        return (HttpURLConnection) new URL(server.getURL(), spec).openConnection();
    }
}