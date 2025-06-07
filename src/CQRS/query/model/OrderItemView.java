package query.model;

import common.exception.OrderExceptions;

import java.util.UUID;

public class OrderItemView {
    private final String id;
    private final String dish;
    private final int quantity;
    private final double price;

    public OrderItemView(String id, String dish, int quantity, double price) {
        if (quantity <= 0) {
            throw new OrderExceptions.InvalidQuantityException();
        }
        if (price <= 0.0) {
            throw new OrderExceptions.InvalidPriceException();
        }
        if (dish == null || dish.trim().isEmpty()) {
            throw new OrderExceptions.BlankDishException();
        }
        if (id == null || id.trim().isEmpty()) {
            throw new OrderExceptions.BlankOrderIdException();
        }
        this.id = id;
        this.dish = dish;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItemView(String dish, int quantity, double price) {
        this(UUID.randomUUID().toString(), dish, quantity, price);
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

    public double getSubtotal() {
        return quantity * price;
    }
}
