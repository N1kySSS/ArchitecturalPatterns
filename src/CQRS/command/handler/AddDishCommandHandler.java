package command.handler;

import command.command.AddDishToOrderCommand;
import command.model.Order;
import command.model.OrderItem;
import command.repository.OrderRepository;
import common.exception.OrderExceptions;

public class AddDishCommandHandler implements CommandHandler<AddDishToOrderCommand> {
    private final OrderRepository orderRepository;

    public AddDishCommandHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void handle(AddDishToOrderCommand command) {
        Order order = orderRepository.findById(command.getOrderId());
        if (order == null) {
            throw new OrderExceptions.OrderNotFoundException(command.getOrderId());
        }

        OrderItem orderItem = new OrderItem(
                command.getDish(),
                command.getQuantity(),
                command.getPrice()
        );

        order.addItem(orderItem);
        orderRepository.save(order);
    }
}
