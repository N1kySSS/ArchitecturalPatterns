package HexagonalArchitecture;

import HexagonalArchitecture.adapter.primary.ConsoleManager;
import HexagonalArchitecture.adapter.secondary.notification.ConsoleNotificationService;
import HexagonalArchitecture.adapter.secondary.persistance.InMemoryOrderRepository;
import HexagonalArchitecture.adapter.secondary.persistance.InMemoryProductRepository;
import HexagonalArchitecture.domain.port.primary.OrderManagementUseCase;
import HexagonalArchitecture.domain.port.secondary.NotificationRepository;
import HexagonalArchitecture.domain.port.secondary.OrderRepository;
import HexagonalArchitecture.domain.port.secondary.ProductRepository;
import HexagonalArchitecture.domain.service.OrderService;

public class Application {
    public static void main(String[] args) {
        OrderRepository orderRepository = new InMemoryOrderRepository();
        ProductRepository productRepository = new InMemoryProductRepository();
        NotificationRepository notificationRepository = new ConsoleNotificationService();

        OrderManagementUseCase orderManagementService = new OrderService(
                orderRepository,
                productRepository,
                notificationRepository
        );

        ConsoleManager consoleUI = new ConsoleManager(orderManagementService);

        System.out.println("Запуск системы управления заказами.");
        consoleUI.start();
    }
}
