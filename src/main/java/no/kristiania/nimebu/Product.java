package no.kristiania.nimebu;

public class Product {
    private String productBrand;
    private String name;
    private Long id;

    public Product(String productBrand, String productName) {
        this.productBrand = productBrand;
        this.name = productName;
    }

    public void setProductBrand(String productBrand) {this.productBrand = productBrand;}
    public String getProductBrand() {return productBrand;}

    public void setName(String name) {this.name = name;}
    public String getName() {return name;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
