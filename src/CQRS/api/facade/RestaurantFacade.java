package api.facade;

import command.command.*;
import command.handler.CommandBus;
import common.event.OrderStatus;
import query.dto.OrderDTO;
import query.dto.OrderStatisticsDTO;
import query.service.OrderQueryService;

import java.util.List;

public class RestaurantFacade {
    private final CommandBus commandBus;
    private final OrderQueryService queryService;

    public RestaurantFacade(CommandBus commandBus, OrderQueryService queryService) {
        this.commandBus = commandBus;
        this.queryService = queryService;
    }

    public void createOrder(String customerId, int tableNumber) {
        commandBus.dispatch(new CreateOrderCommand(customerId, tableNumber));
    }

    public void addDishToOrder(String orderId, String dish, int quantity, double price) {
        commandBus.dispatch(new AddDishToOrderCommand(orderId, dish, quantity, price));
    }

    public void removeDishFromOrder(String orderId, String dish) {
        commandBus.dispatch(new RemoveDishFromOrderCommand(orderId, dish));
    }

    public void updateOrderStatus(String orderId, OrderStatus newStatus) {
        commandBus.dispatch(new UpdateOrderStatusCommand(orderId, newStatus));

        if (newStatus == OrderStatus.COMPLETED || newStatus == OrderStatus.CANCELLED) {
            queryService.deleteById(orderId, newStatus);
        }
    }

    public OrderDTO getOrder(String orderId) {
        return queryService.getOrderById(orderId);
    }

    public List<OrderDTO> getAllOrders() {
        return queryService.getAllOrders();
    }

    public OrderStatisticsDTO getOrderStatistics() {
        return queryService.getOrderStatistics();
    }
}
