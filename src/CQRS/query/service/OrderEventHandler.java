package query.service;

import common.event.*;
import query.model.OrderItemView;
import query.model.OrderView;
import query.repository.OrderViewRepository;

public class OrderEventHandler implements EventBus.EventHandler {
    private final OrderViewRepository repository;

    public OrderEventHandler(OrderViewRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(Event event) {
        switch (event) {
            case OrderCreatedEvent orderCreatedEvent -> handleOrderCreated(orderCreatedEvent);
            case DishAddedEvent dishAddedEvent -> handleDishAdded(dishAddedEvent);
            case DishRemovedEvent dishRemovedEvent -> handleDishRemoved(dishRemovedEvent);
            case OrderStatusChangedEvent orderStatusChangedEvent -> handleOrderStatusChanged(orderStatusChangedEvent);
            case null, default -> {
                /* do nothing - неизвестное событие */
            }
        }
    }

    private void handleOrderCreated(OrderCreatedEvent event) {
        OrderView orderView = new OrderView(
                event.getOrderId(),
                event.getCustomerId(),
                event.getTableNumber(),
                OrderStatus.CREATED
        );
        repository.save(orderView);
    }

    private void handleDishAdded(DishAddedEvent event) {
        OrderView orderView = repository.findById(event.getOrderId());
        if (orderView == null) {
            return;
        }

        int itemIndex = -1;
        OrderItemView existingItem = null;

        for (int i = 0; i < orderView.getItems().size(); i++) {
            OrderItemView currentItem = orderView.getItems().get(i);

            if (currentItem.getDish().equals(event.getDish())) {
                itemIndex = i;
                existingItem = currentItem;
                break;
            }
        }

        if (existingItem != null) {
            OrderItemView updatedItem = new OrderItemView(
                    existingItem.getId(),
                    existingItem.getDish(),
                    existingItem.getQuantity() + event.getQuantity(),
                    existingItem.getPrice()
            );
            orderView.setItem(itemIndex, updatedItem);
        } else {
            OrderItemView newItem = new OrderItemView(
                    event.getDish(),
                    event.getQuantity(),
                    event.getPrice()
            );
            orderView.addItem(newItem);
        }

        repository.save(orderView);
    }

    private void handleDishRemoved(DishRemovedEvent event) {
        OrderView orderView = repository.findById(event.getOrderId());
        if (orderView == null) {
            return;
        }
        orderView.removeItem(event.getDish());
        repository.save(orderView);
    }

    private void handleOrderStatusChanged(OrderStatusChangedEvent event) {
        OrderView orderView = repository.findById(event.getOrderId());
        if (orderView == null) {
            return;
        }
        orderView.updateStatus(event.getNewStatus());
        repository.save(orderView);
    }
}
