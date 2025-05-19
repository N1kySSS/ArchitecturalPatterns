package HexagonalArchitecture.domain.service;


import HexagonalArchitecture.domain.model.Order;
import HexagonalArchitecture.domain.model.OrderItem;
import HexagonalArchitecture.domain.model.OrderStatus;
import HexagonalArchitecture.domain.model.Product;
import HexagonalArchitecture.domain.port.primary.OrderManagementUseCase;
import HexagonalArchitecture.domain.port.secondary.NotificationRepository;
import HexagonalArchitecture.domain.port.secondary.OrderRepository;
import HexagonalArchitecture.domain.port.secondary.ProductRepository;

import java.util.List;

public class OrderService implements OrderManagementUseCase {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final NotificationRepository notificationRepository;

    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            NotificationRepository notificationRepository
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public String createOrder(List<OrderItem> products) {
        Order newOrder = new Order(products);
        orderRepository.save(newOrder);
        return newOrder.getId();
    }

    @Override
    public void addProductToOrder(String orderId, String productId, int quantity) {
        Order order = getOrderOrThrow(orderId);
        Product product = getProductOrThrow(productId);

        order.addItem(product, quantity);
        orderRepository.save(order);
    }

    @Override
    public void removeProductFromOrder(String orderId, int itemIndex) {
        Order order = getOrderOrThrow(orderId);

        order.removeItem(itemIndex);
        orderRepository.save(order);
    }

    @Override
    public void changeOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = getOrderOrThrow(orderId);

        order.changeStatus(newStatus);
        orderRepository.save(order);

        if (newStatus == OrderStatus.CONFIRMED) {
            notificationRepository.notifySupplier(orderId);
        }
        notificationRepository.notifyOrderStatusChanged(order);
    }

    @Override
    public List<OrderItem> getOrderItems(String id) {
        return orderRepository.findById(id).getProducts();
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public void checkProductsDate(String id) {
        orderRepository.findById(id).checkProducts();
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteOrder(String orderId) {
        orderRepository.deleteOrder(orderId);
    }


    private Order getOrderOrThrow(String orderId) {
        Order order = orderRepository.findById(orderId);

        if (order == null) {
            throw new IllegalArgumentException("Заказ не найден: " + orderId);
        }

        return  order;
    }

    private Product getProductOrThrow(String productId) {
        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new IllegalArgumentException("Товар не найден: " + productId);
        }

        return  product;
    }
}
