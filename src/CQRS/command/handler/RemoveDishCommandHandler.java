package command.handler;

import command.command.RemoveDishFromOrderCommand;
import command.model.Order;
import command.repository.OrderRepository;
import common.exception.OrderExceptions;

public class RemoveDishCommandHandler implements CommandHandler<RemoveDishFromOrderCommand> {
    private final OrderRepository orderRepository;

    public RemoveDishCommandHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void handle(RemoveDishFromOrderCommand command) {
        Order order = orderRepository.findById(command.getOrderId());
        if (order == null) {
            throw new OrderExceptions.OrderNotFoundException(command.getOrderId());
        }

        order.removeItemByDish(command.getDish());
        orderRepository.save(order);
    }
}
