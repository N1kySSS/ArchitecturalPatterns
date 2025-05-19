package HexagonalArchitecture.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {
    private final String id;
    private double cost;
    private OrderStatus status;
    private final LocalDateTime createdAt;
    private List<OrderItem> products;

    public Order(List<OrderItem> products) {
        this.id = UUID.randomUUID().toString();
        this.products = products;
        this.cost = calculateTotalPrice();
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.CREATED;
    }

    public String getId() {
        return id;
    }

    public List<OrderItem> getProducts() {
        return products;
    }

    public double getCost() {
        return cost;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void addItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество товара должно быть положительным числом");
        }
        products.add(new OrderItem(product, quantity));
    }

    public void removeItem(int index) {
        if (index < 0 || index >= products.size()) {
            throw new IllegalArgumentException("Некорректный индекс товара");
        }
        products.remove(index);
    }

    public void changeStatus(OrderStatus newStatus) {
        if (!isValidStatusTransition(this.status, newStatus)) {
            throw new IllegalStateException("Недопустимый переход статуса: " + this.status + " -> " + newStatus);
        }
        this.status = newStatus;
    }

    public void checkProducts() {
        if (products.isEmpty()) {
            throw new IllegalArgumentException("В заказе нет товаров.");
        }

        LocalDate today = LocalDate.now();
        int counter = 0;

        for (int i = 0; i < products.size(); i++) {
            OrderItem item = products.get(i);
            LocalDate expiry = item.getExpiredDate();

            if (expiry.isBefore(today)) {
                counter++;
                System.out.println("Просрочен продукт [№" + (i + 1) + "]: " + item.getProduct().getName());
            }
        }

        if (counter == 0) {
            System.out.println("Просрочки не обнаружено.");
        }
    }

    private boolean isValidStatusTransition(OrderStatus current, OrderStatus next) {
        return switch (current) {
            case CREATED -> next == OrderStatus.CONFIRMED || next == OrderStatus.CANCELED;
            case CONFIRMED -> next == OrderStatus.GOING || next == OrderStatus.CANCELED;
            case GOING -> next == OrderStatus.DELIVERED || next == OrderStatus.CANCELED;
            case DELIVERED -> next == OrderStatus.CANCELED;
            default -> false;
        };
    }

    public double calculateTotalPrice() {
        double price = products.stream().mapToDouble(OrderItem::getTotalPrice).sum();
        this.cost = price;
        return price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", cost=" + cost +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", products=" + products +
                '}';
    }
}
