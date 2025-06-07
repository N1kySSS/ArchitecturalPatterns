package command.repository;

import command.model.Order;

public interface OrderRepository {
    void save(Order order);

    Order findById(String id);

    void deleteById(String id);
}