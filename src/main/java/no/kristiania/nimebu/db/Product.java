package no.kristiania.nimebu.db;

public class Product {
    private String brand;
    private String name;
    private Long id;

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
