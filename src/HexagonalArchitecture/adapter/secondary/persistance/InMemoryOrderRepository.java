package HexagonalArchitecture.adapter.secondary.persistance;


import HexagonalArchitecture.domain.model.Order;
import HexagonalArchitecture.domain.port.secondary.OrderRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, Order> orders = new HashMap<>();

    @Override
    public void save(Order order) {
        orders.put(order.getId(), order);
    }

    @Override
    public Order findById(String id) {
        return orders.get(id);
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public void deleteOrder(String orderId) {
        orders.remove(orderId);
    }
}
