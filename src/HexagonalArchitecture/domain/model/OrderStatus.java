package HexagonalArchitecture.domain.model;

public enum OrderStatus {
    CREATED("Создан"),
    CONFIRMED("Подтвержден"),
    GOING("В пути"),
    DELIVERED("Доставлен"),
    CANCELED("Отменен");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}