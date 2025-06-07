package command.model;

import common.event.*;
import common.exception.OrderExceptions;

import java.time.LocalDateTime;
import java.util.*;

public class Order {
    private final String id;
    private final String customerId;
    private final int tableNumber;
    private final List<OrderItem> items;
    private OrderStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Order(String id, String customerId, int tableNumber, List<OrderItem> items,
                  OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.customerId = customerId;
        this.tableNumber = tableNumber;
        this.items = new ArrayList<>(items);
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Order(String customerId, int tableNumber) {
        this(
                UUID.randomUUID().toString(),
                customerId,
                tableNumber,
                new ArrayList<>(),
                OrderStatus.CREATED,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        if (tableNumber <= 0) {
            throw new OrderExceptions.InvalidTableNumberException();
        }
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new OrderExceptions.BlankCustomerIdException();
        }

        EventBus.getInstance().publish(
                new OrderCreatedEvent(
                        id,
                        tableNumber,
                        customerId
                )
        );
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void addItem(OrderItem item) {
        validateOrderModifiable();

        int itemIndex = -1;
        OrderItem existingItem = null;

        for (int i = 0; i < items.size(); i++) {
            OrderItem currentItem = items.get(i);

            if (currentItem.getDish().equals(item.getDish())) {
                itemIndex = i;
                existingItem = currentItem;
                break;
            }
        }

        if (existingItem != null) {
            OrderItem updatedItem = new OrderItem(
                    item.getDish(),
                    item.getQuantity() + existingItem.getQuantity(),
                    existingItem.getPrice()
            );
            items.set(itemIndex, updatedItem);
        } else {
            items.add(item);
        }

        updatedAt = LocalDateTime.now();

        EventBus.getInstance().publish(
                new DishAddedEvent(
                        id,
                        item.getDish(),
                        item.getQuantity(),
                        item.getPrice()
                )
        );
    }

    public void removeItemByDish(String dish) {
        validateOrderModifiable();

        OrderItem item = items.stream()
                .filter(i -> i.getDish().equals(dish))
                .findFirst()
                .orElseThrow(() -> new OrderExceptions.OrderItemNotFoundException(dish));

        items.remove(item);
        updatedAt = LocalDateTime.now();

        EventBus.getInstance().publish(
                new DishRemovedEvent(
                        id,
                        dish
                )
        );
    }

    public void updateStatus(OrderStatus newStatus) {
        status.validateOrderStatusTransition(newStatus);
        status = newStatus;
        updatedAt = LocalDateTime.now();

        EventBus.getInstance().publish(
                new OrderStatusChangedEvent(
                        id,
                        newStatus
                )
        );
    }

    private void validateOrderModifiable() {
        if (status != OrderStatus.CREATED) {
            throw new OrderExceptions.OrderNotModifiableException();
        }
    }
}
