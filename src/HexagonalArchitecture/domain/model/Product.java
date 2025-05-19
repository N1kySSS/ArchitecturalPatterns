package HexagonalArchitecture.domain.model;

import java.time.LocalDate;

public class Product {
    private final String id;
    private final String name;
    private final double price;
    private final LocalDate expiredAt;

    public Product(String id, String name, double price, LocalDate expiredAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.expiredAt = expiredAt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getExpiredAt() {
        return expiredAt;
    }

    @Override
    public String toString() {
        return name + " (" + price + " руб.)";
    }
}