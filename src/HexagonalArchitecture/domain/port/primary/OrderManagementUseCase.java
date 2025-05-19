package HexagonalArchitecture.domain.port.primary;

import HexagonalArchitecture.domain.model.Order;
import HexagonalArchitecture.domain.model.OrderItem;
import HexagonalArchitecture.domain.model.OrderStatus;
import HexagonalArchitecture.domain.model.Product;

import java.util.List;

public interface OrderManagementUseCase {
    String createOrder(List<OrderItem> products);

    void addProductToOrder(String orderId, String productId, int quantity);

    void removeProductFromOrder(String orderId, int itemIndex);

    void changeOrderStatus(String orderId, OrderStatus newStatus);

    List<OrderItem>  getOrderItems(String id);

    List<Order> getAllOrders();

    Order getOrderById(String id);

    void checkProductsDate(String id);

    List<Product> findAllProducts();

    void deleteOrder(String orderId);
}
