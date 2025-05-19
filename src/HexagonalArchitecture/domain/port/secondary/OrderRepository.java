package HexagonalArchitecture.domain.port.secondary;


import HexagonalArchitecture.domain.model.Order;

import java.util.List;

public interface OrderRepository {
    void save(Order order);

    Order findById(String id);

    List<Order> findAll();

    void deleteOrder(String orderId);
}
