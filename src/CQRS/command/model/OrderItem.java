package command.model;

import common.exception.OrderExceptions;

import java.util.UUID;

public class OrderItem {
    private final String id;
    private final String dish;
    private final int quantity;
    private final double price;

    private OrderItem(String id, String dish, int quantity, double price) {
        this.id = id;
        this.dish = dish;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItem(String dish, int quantity, double price) {
        this(UUID.randomUUID().toString(), dish, quantity, price);

        if (quantity <= 0) {
            throw new OrderExceptions.InvalidQuantityException();
        }
        if (price <= 0.0) {
            throw new OrderExceptions.InvalidPriceException();
        }
        if (dish == null || dish.trim().isEmpty()) {
            throw new OrderExceptions.BlankDishException();
        }
    }

    public String getId() {
        return id;
    }

    public String getDish() {
        return dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
