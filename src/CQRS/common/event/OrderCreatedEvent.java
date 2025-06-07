package common.event;

import common.exception.OrderExceptions;

public class OrderCreatedEvent extends Event {
    private final String orderId;
    private final int tableNumber;
    private final String customerId;

    public OrderCreatedEvent(String orderId, int tableNumber, String customerId) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new OrderExceptions.BlankOrderIdException();
        }
        if (tableNumber <= 0) {
            throw new OrderExceptions.InvalidTableNumberException();
        }
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new OrderExceptions.BlankCustomerIdException();
        }
        this.orderId = orderId;
        this.tableNumber = tableNumber;
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public String getCustomerId() {
        return customerId;
    }
}
