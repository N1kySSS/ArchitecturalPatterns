package HexagonalArchitecture.adapter.secondary.notification;


import HexagonalArchitecture.domain.model.Order;
import HexagonalArchitecture.domain.port.secondary.NotificationRepository;

public class ConsoleNotificationService implements NotificationRepository {
    @Override
    public void notifySupplier(String orderId) {
        System.out.println("\n[УВЕДОМЛЕНИЕ] Поставщик принял заказ №" + orderId);
    }

    @Override
    public void notifyOrderStatusChanged(Order order) {
        System.out.println("\n[УВЕДОМЛЕНИЕ] Статус заказа #" + order.getId() + " изменен на: " + order.getStatus());
    }
}
