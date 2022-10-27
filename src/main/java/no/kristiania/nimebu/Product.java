package no.kristiania.nimebu;

public class Product {
    private String brand;
    private String name;
    private Long id;

    public Product(String productBrand, String productName) {
        this.brand = productBrand;
        this.name = productName;
    }

    public void setBrand(String brand) {this.brand = brand;}
    public String getBrand() {return brand;}

    public void setName(String name) {this.name = name;}
    public String getName() {return name;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
