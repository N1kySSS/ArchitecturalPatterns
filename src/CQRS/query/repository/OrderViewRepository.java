package query.repository;

import common.event.OrderStatus;
import query.model.OrderView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OrderViewRepository {
    private final List<OrderView> orderViews = new ArrayList<>();
    private int counterOfOrders = 0;
    private int counterOfCompleteOrders = 0;
    private double totalRevenue = 0;

    public void save(OrderView orderView) {
        int existingOrderIndex = -1;
        for (int i = 0; i < orderViews.size(); i++) {
            if (orderViews.get(i).getId().equals(orderView.getId())) {
                existingOrderIndex = i;
                break;
            }
        }
        if (existingOrderIndex != -1) {
            orderViews.set(existingOrderIndex, orderView);
        } else {
            orderViews.add(orderView);
            counterOfOrders += 1;
        }
    }

    public OrderView findById(String id) {
        return orderViews.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<OrderView> findByStatus(OrderStatus status) {
        return orderViews.stream()
                .filter(order -> order.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<OrderView> findAll() {
        return Collections.unmodifiableList(orderViews);
    }


    public void deleteById(String id, OrderStatus status) {
        if (status == OrderStatus.COMPLETED) {
            counterOfCompleteOrders += 1;
            totalRevenue += orderViews.stream()
                    .filter(o -> o.getId().equals(id))
                    .findFirst()
                    .map(OrderView::getTotalAmount)
                    .orElse(0.0);
        }

        orderViews.removeIf(order -> order.getId().equals(id));
    }

    public int getAllOrdersCount() {
        return counterOfOrders;
    }

    public int getCompleteOrdersCount() {
        return counterOfCompleteOrders;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}
