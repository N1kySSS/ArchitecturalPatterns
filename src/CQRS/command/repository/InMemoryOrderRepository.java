package command.repository;

import command.model.Order;

import java.util.ArrayList;
import java.util.List;

public class InMemoryOrderRepository implements OrderRepository {
    private final List<Order> orders = new ArrayList<>();

    @Override
    public void save(Order order) {
        int existingOrderIndex = -1;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equals(order.getId())) {
                existingOrderIndex = i;
                break;
            }
        }
        if (existingOrderIndex != -1) {
            orders.set(existingOrderIndex, order);
        } else {
            orders.add(order);
        }
    }

    @Override
    public Order findById(String id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(String id) {
        orders.removeIf(order -> order.getId().equals(id));
    }
}
