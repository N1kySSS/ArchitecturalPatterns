package query.service;

import common.event.OrderStatus;
import query.dto.OrderDTO;
import query.dto.OrderStatisticsDTO;
import query.model.OrderView;
import query.repository.OrderViewRepository;

import java.util.List;
import java.util.stream.Collectors;

public class OrderQueryService {
    private final OrderViewRepository orderViewRepository;

    public OrderQueryService(OrderViewRepository orderViewRepository) {
        this.orderViewRepository = orderViewRepository;
    }

    public OrderDTO getOrderById(String id) {
        var orderView = orderViewRepository.findById(id);
        return orderView != null ? orderView.toDTO() : null;
    }

    public List<OrderDTO> getAllOrders() {
        return orderViewRepository.findAll().stream()
                .map(OrderView::toDTO)
                .collect(Collectors.toList());
    }

    public OrderStatisticsDTO getOrderStatistics() {
        var allOrders = orderViewRepository.getAllOrdersCount();
        var completedOrders = orderViewRepository.getCompleteOrdersCount();
        var totalRevenue = orderViewRepository.getTotalRevenue();

        return new OrderStatisticsDTO(
                allOrders,
                completedOrders,
                totalRevenue,
                completedOrders != 0 ? totalRevenue / completedOrders : 0.0
        );
    }

    public void deleteById(String id, OrderStatus status) {
        orderViewRepository.deleteById(id, status);
    }
}
