package no.kristiania.nimebu;

public class Product {
    private String productBrand;
    private String name;

    public Product(String productBrand, String productName) {
        this.productBrand = productBrand;
        this.name = productName;
    }

    public void setProductBrand(String productBrand) {this.productBrand = productBrand;}
    public String getProductBrand() {return productBrand;}

    public void setName(String name) {this.name = name;}
    public String getName() {return name;}
}
