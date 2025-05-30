package HexagonalArchitecture.domain.model;

import java.time.LocalDate;

public class OrderItem {
    private final Product product;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    public LocalDate getExpiredDate() {
        return product.getExpiredAt();
    }

    @Override
    public String toString() {
        return product.getName() + " x " + quantity + " = " + getTotalPrice() + " руб.";
    }
}
