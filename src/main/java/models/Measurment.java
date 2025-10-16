package models;

public class Measurment {
    private Product product;
    private Year year;
    private double price;

    public Measurment(Product product, Year year, double price) {
        this.product = product;
        this.year = year;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public Year getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return "Measurment{" +
                "productId=" + product.getId() + '\'' +
                ", year=" + year.getYearValue() +
                ", price=" + price +
                '}';
    }
}
