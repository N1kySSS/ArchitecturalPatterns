package command.handler;

import command.command.CreateOrderCommand;
import command.model.Order;
import command.repository.OrderRepository;

public class CreateOrderCommandHandler implements CommandHandler<CreateOrderCommand> {
    private final OrderRepository orderRepository;

    public CreateOrderCommandHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void handle(CreateOrderCommand command) {
        Order order = new Order(
                command.getCustomerId(),
                command.getTableNumber()
        );

        orderRepository.save(order);
    }
}
