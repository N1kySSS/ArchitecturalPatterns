package common.event;

import common.exception.OrderExceptions;

public class DishAddedEvent extends Event {
    private final String orderId;
    private final String dish;
    private final int quantity;
    private final double price;

    public DishAddedEvent(String orderId, String dish, int quantity, double price) {
        if (quantity <= 0) {
            throw new OrderExceptions.InvalidQuantityException();
        }
        if (price <= 0.0) {
            throw new OrderExceptions.InvalidPriceException();
        }
        if (dish == null || dish.trim().isEmpty()) {
            throw new OrderExceptions.BlankDishException();
        }
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new OrderExceptions.BlankOrderIdException();
        }
        this.orderId = orderId;
        this.dish = dish;
        this.quantity = quantity;
        this.price = price;
    }

    public DishAddedEvent(String orderId, String dish, double price) {
        this(orderId, dish, 1, price);
    }

    public String getOrderId() {
        return orderId;
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
