package models;

public class Product {
    private String name;
    private String alias;
    private int id; //the column this product has

    public Product(String name, String alias, int id){
        this.name = name;
        this.id = id;
        this.alias = alias;
    }

    public int getId() {return this.id;}
    public String getName() {return this.name;}
    public String getAlias(){return this.alias;}
}
