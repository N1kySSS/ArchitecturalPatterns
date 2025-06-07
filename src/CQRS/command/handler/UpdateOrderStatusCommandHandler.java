package command.handler;

import command.command.UpdateOrderStatusCommand;
import command.model.Order;
import command.repository.OrderRepository;
import common.event.OrderStatus;
import common.exception.OrderExceptions;

public class UpdateOrderStatusCommandHandler implements CommandHandler<UpdateOrderStatusCommand> {
    private final OrderRepository orderRepository;

    public UpdateOrderStatusCommandHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void handle(UpdateOrderStatusCommand command) {
        String orderId = command.getOrderId();

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderExceptions.OrderNotFoundException(orderId);
        }

        OrderStatus status = command.getNewStatus();

        order.updateStatus(status);
        orderRepository.save(order);

        if (status == OrderStatus.COMPLETED || status == OrderStatus.CANCELLED) {
            orderRepository.deleteById(orderId);
        }
    }
}
