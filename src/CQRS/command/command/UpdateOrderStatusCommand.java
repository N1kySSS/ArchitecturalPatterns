package command.command;

import common.event.OrderStatus;
import common.exception.OrderExceptions;

public class UpdateOrderStatusCommand implements Command {
    private final String orderId;
    private final OrderStatus newStatus;

    public UpdateOrderStatusCommand(String orderId, OrderStatus newStatus) {
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
