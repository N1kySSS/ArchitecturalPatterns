package HexagonalArchitecture.adapter.primary;


import HexagonalArchitecture.domain.model.Order;
import HexagonalArchitecture.domain.model.OrderItem;
import HexagonalArchitecture.domain.model.OrderStatus;
import HexagonalArchitecture.domain.model.Product;
import HexagonalArchitecture.domain.port.primary.OrderManagementUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ConsoleManager {
    private final OrderManagementUseCase orderManagement;
    private final Scanner scanner;
    private final Random random;
    private String currentOrderId;
    private OrderStatus currentOrderstatus;

    public ConsoleManager(OrderManagementUseCase orderManagement) {
        this.orderManagement = orderManagement;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.currentOrderId = null;
        this.currentOrderstatus = null;
    }

    public void start() {
        int choice;
        do {
            showMainMenu();
            choice = readIntInput();
            scanner.nextLine();
            handleMainMenuChoice(choice);
        } while (choice != 0);
    }

    private void showMainMenu() {
        System.out.println("\n===== Система управления заказами =====");
        if (currentOrderId != null) {
            System.out.println("Текущий заказ: #" + currentOrderId + " | Статус - " + currentOrderstatus);
        }
        System.out.println("1. Создать новый заказ");
        System.out.println("2. Выбрать существующий заказ");
        System.out.println("3. Показать все заказы");

        if (currentOrderId != null) {
            System.out.println("4. Добавить товар в текущий заказ");
            System.out.println("5. Удалить товар из текущего заказа");
            System.out.println("6. Изменить статус текущего заказа");
            System.out.println("7. Показать детали текущего заказа");
        }

        if (currentOrderstatus == OrderStatus.DELIVERED) {
            System.out.println("8. Проверить качество продуктов в заказе");
        }

        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void handleMainMenuChoice(int choice) {
        switch (choice) {
            case 0:
                System.out.println("Выход из программы...");
                break;
            case 1:
                createNewOrder();
                break;
            case 2:
                selectExistingOrder();
                break;
            case 3:
                showAllOrders();
                break;
            case 4:
                if (currentOrderId != null) addProductToOrder();
                else showNoOrderSelectedMessage();
                break;
            case 5:
                if (currentOrderId != null) removeProductFromOrder();
                else showNoOrderSelectedMessage();
                break;
            case 6:
                if (currentOrderId != null) changeOrderStatus();
                else showNoOrderSelectedMessage();
                break;
            case 7:
                if (currentOrderId != null) showOrderDetails();
                else showNoOrderSelectedMessage();
                break;
            case 8:
                if (currentOrderstatus == OrderStatus.DELIVERED) checkProductsInOrder();
                else showNoOrderSelectedMessage();
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private void createNewOrder() {
        List<Product> forecastProducts = orderManagement.findAllProducts();
        List<OrderItem> products = new ArrayList<>();

        for (Product product : forecastProducts) {
            int quantity = 5 + random.nextInt(26);
            products.add(new OrderItem(product, quantity));
        }

        currentOrderId = orderManagement.createOrder(products);
        currentOrderstatus = orderManagement.getOrderById(currentOrderId).getStatus();
        System.out.println("Создан новый заказ #" + currentOrderId + " | Статус - " + currentOrderstatus);
    }

    private void selectExistingOrder() {
        List<Order> orders = orderManagement.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("Нет доступных заказов.");
            return;
        }

        System.out.println("\n=== Доступные заказы ===");
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            System.out.println((i + 1) + ". Заказ #" + order.getId() + " (" + order.getStatus() + ")");
        }

        System.out.print("Выберите номер заказа (1-" + orders.size() + "): ");
        int orderIndex = readIntInput() - 1;
        scanner.nextLine();

        if (orderIndex >= 0 && orderIndex < orders.size()) {
            currentOrderId = orders.get(orderIndex).getId();
            System.out.println("Выбран заказ #" + currentOrderId);
        } else {
            System.out.println("Некорректный выбор заказа.");
        }
    }

    private void showAllOrders() {
        List<Order> orders = orderManagement.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("Нет доступных заказов.");
            return;
        }

        System.out.println("\n=== Все заказы ===");
        for (Order order : orders) {
            System.out.println("Заказ #" + order.getId());
            System.out.println("Статус: " + order.getStatus());
            System.out.println("Сумма: " + order.calculateTotalPrice() + " руб.");
            System.out.println("Товары: " + order.getProducts().size());
            System.out.println("-----------------------");
        }
    }

    private void addProductToOrder() {
        List<Product> products = orderManagement.findAllProducts();

        if (products.isEmpty()) {
            System.out.println("Нет доступных товаров.");
            return;
        }

        System.out.println("\n=== Доступные товары ===");
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.println((i + 1) + ". " + product);
        }

        System.out.print("Выберите номер товара (1-" + products.size() + "): ");
        int productIndex = readIntInput() - 1;
        scanner.nextLine();

        if (productIndex < 0 || productIndex >= products.size()) {
            System.out.println("Некорректный выбор товара.");
            return;
        }

        System.out.print("Введите количество: ");
        int quantity = readIntInput();
        scanner.nextLine();

        try {
            String productId = products.get(productIndex).getId();
            orderManagement.addProductToOrder(currentOrderId, productId, quantity);
            System.out.println("Товар успешно добавлен в заказ.");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении товара: " + e.getMessage());
        }
    }

    private void removeProductFromOrder() {
        Order orderOpt = orderManagement.getOrderById(currentOrderId);

        if (orderOpt == null) {
            System.out.println("Заказ не найден.");
            return;
        }

        if (orderOpt.getProducts().isEmpty()) {
            System.out.println("В заказе нет товаров.");
            return;
        }

        System.out.println("\n=== Товары в заказе ===");
        for (int i = 0; i < orderOpt.getProducts().size(); i++) {
            System.out.println((i + 1) + ". " + orderOpt.getProducts().get(i));
        }

        System.out.print("Выберите номер товара для удаления (1-" + orderOpt.getProducts().size() + "): ");
        int itemIndex = readIntInput() - 1;
        scanner.nextLine();

        try {
            orderManagement.removeProductFromOrder(currentOrderId, itemIndex);
            System.out.println("Товар успешно удален из заказа.");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении товара: " + e.getMessage());
        }
    }

    private void changeOrderStatus() {
        Order orderOpt = orderManagement.getOrderById(currentOrderId);

        if (orderOpt == null) {
            System.out.println("Заказ не найден.");
            return;
        }

        OrderStatus currentStatus = orderOpt.getStatus();

        System.out.println("\nТекущий статус заказа: " + currentStatus);

        OrderStatus[] availableStatuses = OrderStatus.values();
        System.out.println("Доступные статусы:");
        for (int i = 0; i < availableStatuses.length; i++) {
            System.out.println((i + 1) + ". " + availableStatuses[i]);
        }

        System.out.print("Выберите новый статус (1-" + availableStatuses.length + "): ");
        int statusIndex = readIntInput() - 1;
        scanner.nextLine();

        if (statusIndex < 0 || statusIndex >= availableStatuses.length) {
            System.out.println("Некорректный выбор статуса.");
            return;
        }

        OrderStatus newStatus = availableStatuses[statusIndex];

        try {
            orderManagement.changeOrderStatus(currentOrderId, newStatus);
            currentOrderstatus = newStatus;
            System.out.println("Статус заказа успешно изменен на: " + newStatus);
        } catch (Exception e) {
            System.out.println("Ошибка при изменении статуса: " + e.getMessage());
        }

        if (currentOrderstatus == OrderStatus.CANCELED) {
            try {
                orderManagement.deleteOrder(currentOrderId);
                currentOrderId = null;
                System.out.println("Заказ успешно удален");
            } catch (Exception e) {
                System.out.println("Ошибка при удалении заказа: " + e.getMessage());
            }
        }
    }

    private void showOrderDetails() {
        Order orderOpt = orderManagement.getOrderById(currentOrderId);

        if (orderOpt == null) {
            System.out.println("Заказ не найден.");
            return;
        }

        System.out.println("\n" + orderOpt);
    }

    private void checkProductsInOrder() {
        try {
            orderManagement.checkProductsDate(currentOrderId);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showNoOrderSelectedMessage() {
        System.out.println("Сначала необходимо создать или выбрать заказ.");
    }

    private int readIntInput() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            return -1;
        }
    }
}
