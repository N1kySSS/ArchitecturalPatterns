package HexagonalArchitecture.domain.port.secondary;


import HexagonalArchitecture.domain.model.Order;

public interface NotificationRepository {
    void notifyOrderStatusChanged(Order order);

    void notifySupplier(String orderId);
}
