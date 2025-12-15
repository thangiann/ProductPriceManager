package models;

import dto.*;
import loader.DataLoader;


public class Measurement {
    private Product product;
    private int year;
    private double price;
    private DataLoader dl;

    public Measurement(int year, Product product, DataLoader data) {
        this.year = year;
        this.dl = data;
        this.product = product;
    }


    public MeasurementDTO createMeasurmentDTO(){
        this.price = findPrice(); 
        return new MeasurementDTO(year, product.getAlias(), price);
    }

    //helper functions
    private double findPrice(){
        String[] yearLine = this.dl.getData().get(year - 1959);
        String priceNumber = yearLine[this.product.getId()];
        return Double.parseDouble(priceNumber);
    }

    //getters
    public Product getProduct() {return product;}
    public int getYear() {return year;}
    public double getPrice() {return price;}


    public String toString() {
        return "Measurment{" +
                "productId=" + product.getId() + '\'' +
                ", year=" + year +
                ", price=" + price +'}';}
}