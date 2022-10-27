package no.kristiania.nimebu;

public class Product {
    private int id;
    private String name;

    public Product(int productId, String productName) {
        this.id = productId;
        this.name = productName;
    }

    public void setId(int id) {this.id = id;}
    public int getId() {return id;}

    public void setName(String name) {this.name = name;}
    public String getName() {return name;}
}
