package query.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private final String id;
    private final String customerId;
    private final int tableNumber;
    private final String status;
    private final List<OrderItemDTO> items;
    private final double totalAmount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public OrderDTO(String id, String customerId, int tableNumber, String status, List<OrderItemDTO> items, double totalAmount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.customerId = customerId;
        this.tableNumber = tableNumber;
        this.status = status;
        this.items = items;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getStatus() {
        return status;
    }

    public List<OrderItemDTO> getItems() {
        return items;
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
}
