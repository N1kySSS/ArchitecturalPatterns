package query.model;

import common.event.OrderStatus;
import common.exception.OrderExceptions;
import query.dto.OrderDTO;
import query.dto.OrderItemDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OrderView {
    private final String id;
    private final String customerId;
    private final int tableNumber;
    private OrderStatus status;
    private final List<OrderItemView> items;
    private double totalAmount;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderView(String id, String customerId, int tableNumber, OrderStatus status) {
        if (id == null || id.trim().isEmpty()) {
            throw new OrderExceptions.BlankOrderIdException();
        }
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new OrderExceptions.BlankCustomerIdException();
        }
        if (tableNumber <= 0) {
            throw new OrderExceptions.InvalidTableNumberException();
        }

        this.id = id;
        this.customerId = customerId;
        this.tableNumber = tableNumber;
        this.status = status;
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
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

    public OrderStatus getStatus() {
        return status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<OrderItemView> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(OrderItemView item) {
        items.add(item);
        recalculateTotalAmount();
        updateTimestamp();
    }

    public void setItem(int index, OrderItemView item) {
        items.set(index, item);
        recalculateTotalAmount();
        updateTimestamp();
    }

    public void removeItem(String dish) {
        items.removeIf(item -> item.getDish().equals(dish));
        recalculateTotalAmount();
        updateTimestamp();
    }

    public void updateStatus(OrderStatus newStatus) {
        status.validateOrderStatusTransition(newStatus);
        this.status = newStatus;
        updateTimestamp();
    }

    private void recalculateTotalAmount() {
        this.totalAmount = items.stream()
                .mapToDouble(OrderItemView::getSubtotal)
                .sum();
    }

    private void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    private List<OrderItemDTO> orderItemListToDTO() {
        return items.stream()
                .map(item -> new OrderItemDTO(
                        item.getId(),
                        item.getDish(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());
    }

    public OrderDTO toDTO() {
        return new OrderDTO(
                id,
                customerId,
                tableNumber,
                status.name(),
                orderItemListToDTO(),
                totalAmount,
                createdAt,
                updatedAt
        );
    }
}
