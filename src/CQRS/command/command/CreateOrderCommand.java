package command.command;

import common.exception.OrderExceptions;

public class CreateOrderCommand implements Command {
    private final String customerId;
    private final int tableNumber;

    public CreateOrderCommand(String customerId, int tableNumber) {
        if (tableNumber <= 0) {
            throw new OrderExceptions.InvalidTableNumberException();
        }
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new OrderExceptions.BlankCustomerIdException();
        }
        this.customerId = customerId;
        this.tableNumber = tableNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getTableNumber() {
        return tableNumber;
    }
}
