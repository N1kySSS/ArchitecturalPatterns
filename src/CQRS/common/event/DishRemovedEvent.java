package common.event;

import common.exception.OrderExceptions;

public class DishRemovedEvent extends Event {
    private final String orderId;
    private final String dish;

    public DishRemovedEvent(String orderId, String dish) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new OrderExceptions.BlankOrderIdException();
        }
        if (dish == null || dish.trim().isEmpty()) {
            throw new OrderExceptions.OrderItemNotFoundException(dish);
        }
        this.orderId = orderId;
        this.dish = dish;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDish() {
        return dish;
    }
}
