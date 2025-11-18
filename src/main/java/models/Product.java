package models;

public class Product {
    
    private String name;
    private int id; //the coolumn this product has

    public Product(String name, int id){
        this.name = name;
        this.id = id;
    }

    //getters
    public int getId() {return this.id;}
    public String getName() {return this.name;}
}
