package common.event;

import common.exception.OrderExceptions;

public class OrderStatusChangedEvent extends Event {
    private final String orderId;
    private final OrderStatus newStatus;

    public OrderStatusChangedEvent(String orderId, OrderStatus newStatus) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new OrderExceptions.BlankOrderIdException();
        }
        this.orderId = orderId;
        this.newStatus = newStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderStatus getNewStatus() {
        return newStatus;
    }
}
