package api.ui;

import api.facade.RestaurantFacade;
import common.event.OrderStatus;
import query.dto.OrderDTO;
import query.dto.OrderItemDTO;
import query.dto.OrderStatisticsDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ConsoleInterface {
    private final RestaurantFacade restaurantFacade;
    private final Scanner scanner;
    private final Random random;
    private final DateTimeFormatter dateFormatter;

    public ConsoleInterface(RestaurantFacade restaurantFacade) {
        this.restaurantFacade = restaurantFacade;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    }

    public void start() {
        int choice;
        do {
            showMainMenu();
            choice = readIntInput();
            handleMainMenuChoice(choice);
        } while (choice != 0);
    }

    private void showMainMenu() {
        System.out.println("\n===== Система управления заказами ресторана =====");
        System.out.println("1. Создать новый заказ");
        System.out.println("2. Показать все заказы");
        System.out.println("3. Информация о заказе");
        System.out.println("4. Добавить блюдо в заказ");
        System.out.println("5. Удалить блюдо из заказа");
        System.out.println("6. Обновить статус заказа");
        System.out.println("7. Статистика заказов");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void handleMainMenuChoice(int choice) {
        switch (choice) {
            case 0:
                System.out.println("Выход из программы...");
                break;
            case 1:
                handleCreateOrder();
                break;
            case 2:
                showAllOrders();
                break;
            case 3:
                showOrderDetails();
                break;
            case 4:
                addDishToOrder();
                break;
            case 5:
                removeDishFromOrder();
                break;
            case 6:
                updateOrderStatus();
                break;
            case 7:
                showOrderStatistics();
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private void printOrderDetails(String orderId) {
        OrderDTO order = restaurantFacade.getOrder(orderId);
        if (order != null) {
            System.out.println("\nДетали заказа:");
            System.out.println("ID заказа: " + order.getId());
            System.out.println("ID клиента: " + order.getCustomerId());
            System.out.println("Номер столика: " + order.getTableNumber());
            System.out.println("Статус заказа: " + order.getStatus());
            System.out.println("\nБлюда:");
            for (OrderItemDTO item : order.getItems()) {
                System.out.printf("- %s (x%d) - %.2f руб. общая стоимость = %.2f руб.%n",
                        item.getDish(), item.getQuantity(), item.getPrice(), item.getSubtotal());
            }
            System.out.printf("%nВсего: %.2f руб.%n", order.getTotalAmount());
        } else {
            System.out.println("Заказ не найден!");
        }
    }

    private void handleCreateOrder() {
        System.out.print("Введите id клиента: ");
        String customerId = scanner.nextLine();

        System.out.print("Введите номер столика: ");
        int tableNumber = readIntInput();

        try {
            restaurantFacade.createOrder(customerId, tableNumber);
            List<OrderDTO> orders = restaurantFacade.getAllOrders();
            String orderId = orders.getLast().getId();

            while (true) {
                System.out.print("\nЕсли хотите добавить блюдо в заказ, напишите \"Да\": ");
                String answer = scanner.nextLine().toLowerCase();
                if (answer.equals("да")) {
                    System.out.print("Введите название блюда: ");
                    String dish = scanner.nextLine();

                    System.out.print("Введите количество: ");
                    int quantity = readIntInput();

                    double price = random.nextDouble(50, 501) + random.nextDouble(1);

                    try {
                        restaurantFacade.addDishToOrder(orderId, dish, quantity, price);
                        System.out.println("Блюдо успешно добавлено в заказ!");
                    } catch (Exception e) {
                        System.out.println("Ошибка добавления блюда в заказ: " + e.getMessage());
                    }
                } else if (answer.equals("нет")) {
                    break;
                } else {
                    System.out.println("Неверный ввод. Введите 'Да' или 'Нет'.");
                }
            }

            System.out.println("\nЗаказ успешно создан!");
            printOrderDetails(orderId);
        } catch (Exception e) {
            System.out.println("Ошибка создания заказа: " + e.getMessage());
        }
    }

    private void showAllOrders() {
        List<OrderDTO> orders = restaurantFacade.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("Нет доступных заказов.");
            return;
        }

        System.out.println("\n=== Все заказы ===");
        System.out.printf("%-36s %-15s %-15s %-15s %-15s%n",
                "ID", "Клиент", "Столик", "Статус", "Сумма");
        System.out.println("-".repeat(100));

        for (OrderDTO order : orders) {
            System.out.printf("%-36s %-15s %-15d %-15s %,.2f руб.%n",
                    order.getId(),
                    order.getCustomerId(),
                    order.getTableNumber(),
                    order.getStatus(),
                    order.getTotalAmount());
        }
    }

    private void showOrderDetails() {
        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine().trim();

        OrderDTO order = restaurantFacade.getOrder(orderId);
        if (order == null) {
            System.out.println("Заказ не найден");
            return;
        }

        System.out.println("\n=== Информация о заказе ===");
        System.out.println("ID заказа: " + order.getId());
        System.out.println("ID клиента: " + order.getCustomerId());
        System.out.println("Номер столика: " + order.getTableNumber());
        System.out.println("Статус: " + order.getStatus());
        System.out.printf("Общая сумма: %.2f руб.%n", order.getTotalAmount());
        System.out.println("Создан: " + order.getCreatedAt().format(dateFormatter));
        System.out.println("Последнее обновление: " + order.getUpdatedAt().format(dateFormatter));

        if (!order.getItems().isEmpty()) {
            System.out.println("\nБлюда в заказе:");
            System.out.printf("%-36s %-15s %-10s %-15s%n",
                    "ID", "Блюдо", "Кол-во", "Цена за 1шт.");
            System.out.println("-".repeat(80));
            for (OrderItemDTO item : order.getItems()) {
                System.out.printf("%-36s %-15s %-10d %,.2f руб.%n",
                        item.getId(),
                        item.getDish(),
                        item.getQuantity(),
                        item.getPrice());
            }
        }
    }

    private void addDishToOrder() {
        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine().trim();

        OrderDTO order = restaurantFacade.getOrder(orderId);
        if (order == null) {
            System.out.println("Заказ не найден");
            return;
        }

        System.out.print("Введите название блюда: ");
        String dish = scanner.nextLine().trim();

        System.out.print("Введите количество: ");
        int quantity = readIntInput();

        double price = random.nextDouble(50, 501) + random.nextDouble(1);

        try {
            restaurantFacade.addDishToOrder(orderId, dish, quantity, price);
            System.out.println("Блюдо успешно добавлено в заказ!");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении блюда: " + e.getMessage());
        }
    }

    private void removeDishFromOrder() {
        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine().trim();

        OrderDTO order = restaurantFacade.getOrder(orderId);
        if (order == null) {
            System.out.println("Заказ не найден");
            return;
        }

        System.out.print("Введите название позиции в заказе: ");
        String dish = scanner.nextLine().trim();

        try {
            restaurantFacade.removeDishFromOrder(orderId, dish);
            System.out.println("Блюдо успешно удалено из заказа!");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении блюда: " + e.getMessage());
        }
    }

    private void updateOrderStatus() {
        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine().trim();

        OrderDTO order = restaurantFacade.getOrder(orderId);
        if (order == null) {
            System.out.println("Заказ не найден");
            return;
        }

        System.out.println("\nДоступные статусы:");
        OrderStatus[] statuses = OrderStatus.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.println((i + 1) + ". " + statuses[i]);
        }
        System.out.print("Выберите новый статус (номер): ");
        int statusOrdinal = readIntInput();

        try {
            OrderStatus newStatus = OrderStatus.values()[statusOrdinal - 1];
            restaurantFacade.updateOrderStatus(orderId, newStatus);
            System.out.println("Статус заказа успешно обновлен!");
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении статуса: " + e.getMessage());
        }
    }

    private void showOrderStatistics() {
        OrderStatisticsDTO statistics = restaurantFacade.getOrderStatistics();
        System.out.println("\n=== Статистика заказов ===");
        System.out.println("Всего заказов: " + statistics.getTotalOrders());
        System.out.println("Выполненных заказов: " + statistics.getCompletedOrders());
        System.out.printf("Общая выручка: %.2f руб.%n", statistics.getTotalRevenue());
        System.out.printf("Средний чек: %.2f руб.%n", statistics.getAverageOrderValue());
    }

    private int readIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число: ");
            }
        }
    }
}
