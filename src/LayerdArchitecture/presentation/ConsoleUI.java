package LayerdArchitecture.presentation;

import LayerdArchitecture.application.InventoryService;
import LayerdArchitecture.domain.Product;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final InventoryService inventoryService;
    private final Scanner scanner;


    public ConsoleUI(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера
            handleMenuChoice(choice);
        } while (choice != 0);
    }

    private void showMenu() {
        System.out.println("\n===== Система инвентаризации ресторана =====");
        System.out.println("1. Пополнить запасы продукта");
        System.out.println("2. Посмотреть продукт");
        System.out.println("3. Сопоставить запасы");
        System.out.println("4. Посмотреть критические продукты");
        System.out.println("5. Использовать продукт");
        System.out.println("6. Убрать просрочку");
        System.out.println("7. Посмотреть все продукты");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                replenishProductStock();
                break;
            case 2:
                showProductByName();
                break;
            case 3:
                viewStock();
                break;
            case 4:
                checkCriticalProducts();
                break;
            case 5:
                useProduct();
                break;
            case 6:
                removeExpiredProducts();
                break;
            case 7:
                showAllProducts();
                break;
            case 0:
                System.out.println("Выход из программы...");
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    public void replenishProductStock() {
        System.out.print("Введите название продукта: ");
        String productName = scanner.nextLine();
        System.out.print("Введите количество продукта: ");
        int amount = 0;

        try {
            amount = Integer.parseInt(scanner.nextLine());
            if (amount < 0) {
                System.out.println("Введено отрицательное количество. Установлено значение 0.");
                amount = 0;
            }
        } catch (Exception e) {
            System.out.println("Введено неверное количество продукта для пополнения.");
        }

        System.out.println("Имитация заказа доставки продукта.");
        System.out.println(".\n..\n...");
        System.out.println("Доставка выполнена");

        try {
            inventoryService.adjustInventory(productName, amount);
            System.out.println("Количество продукта успешно пополнено");
        } catch (Exception e) {
            System.out.println("Ошибка при пополнении запасов продукта: " + e.getMessage());
        }
    }

    public void showProductByName() {
        System.out.print("Введите название продукта: ");
        String productName = scanner.nextLine();

        try {
            Product product = inventoryService.getProduct(productName);
            System.out.println(product.toString());
        } catch (Exception e) {
            System.out.println("Ошибка при попытке посмотреть продукт: " + e.getMessage());
        }
    }

    public void viewStock() {
        System.out.print("Введите название продукта: ");
        String productName = scanner.nextLine();
        System.out.print("Введите ожидаемое количество продукта: ");
        int amount = 0;

        try {
            amount = Integer.parseInt(scanner.nextLine());
            if (amount < 0) {
                System.out.println("Введено отрицательное количество. Установлено значение 0.");
                amount = 0;
            }
        } catch (Exception e) {
            System.out.println("Введено неверное количество продукта для пополнения.");
        }

        try {
            boolean result = inventoryService.checkStock(productName, amount);
            System.out.println("Соответствует ли ожидаемый результат с реальным: " + result);

            if (!result) {
                Product product = inventoryService.getProduct(productName);
                inventoryService.adjustInventory(productName, amount - product.getQuantity());
                System.out.println("Результат откорректирован");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при попытке сопоставить данные: " + e.getMessage());
        }
    }

    public void checkCriticalProducts() {
        try {
            List<Product> criticalProducts = inventoryService.viewCritical();
            System.out.println("Продукты с критическим количеством:");
            criticalProducts.forEach(it -> System.out.println(it.toString()));
        } catch (Exception e) {
            System.out.println("Ошибка при попытке проверки продуктов: " + e.getMessage());
        }
    }

    public void useProduct() {
        System.out.print("Введите название продукта: ");
        String productName = scanner.nextLine();
        System.out.print("Введите количество продукта: ");
        int amount = 0;

        try {
            amount = Integer.parseInt(scanner.nextLine());
            if (amount < 0) {
                System.out.println("Введено отрицательное количество. Установлено значение 0.");
                amount = 0;
            }
        } catch (Exception e) {
            System.out.println("Введено неверное количество продукта для пополнения.");
        }

        try {
            inventoryService.useProduct(productName, amount);
            System.out.println("Продукт успешно использован в приготовлении блюда, его количество уменьшено.");
        } catch (Exception e) {
            System.out.println("Ошибка при пополнении запасов продукта: " + e.getMessage());
        }
    }

    public void removeExpiredProducts() {
        try {
            inventoryService.removeExpired();
            System.out.println("Просроченные продукты успешно убраны.");
        } catch (Exception e) {
            System.out.println("Ошибка при попытке убрать просроченные продукты: " + e.getMessage());
        }
    }

    public void showAllProducts() {
        List<Product> products = inventoryService.viewAll();
        if (products.isEmpty()) {
            System.out.println("В инвентаре нет продуктов.");
            return;
        }

        System.out.println("=== Все продукты ===");
        for (Product member : products) {
            System.out.println(member);
        }
    }
}
