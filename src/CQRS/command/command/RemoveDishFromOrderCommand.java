package command.command;

import common.exception.OrderExceptions;

public class RemoveDishFromOrderCommand implements Command {
    private final String orderId;
    private final String dish;

    public RemoveDishFromOrderCommand(String orderId, String dish) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new OrderExceptions.BlankOrderIdException();
        }
        if (dish == null || dish.trim().isEmpty()) {
            throw new OrderExceptions.BlankDishException();
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
