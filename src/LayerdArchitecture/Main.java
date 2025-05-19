package LayerdArchitecture;

import LayerdArchitecture.application.InventoryService;
import LayerdArchitecture.domain.IProductRepository;
import LayerdArchitecture.domain.TemperatureMode;
import LayerdArchitecture.infrastructure.InMemoryProductRepository;
import LayerdArchitecture.presentation.ConsoleUI;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        IProductRepository productRepository = new InMemoryProductRepository();

        InventoryService inventoryService = new InventoryService(productRepository);

        inventoryService.addProduct("Молоко", 50, LocalDate.now().plusDays(7), TemperatureMode.Chilled, 20, 60, 10);
        inventoryService.addProduct("Яйца", 200, LocalDate.now().plusDays(14), TemperatureMode.Normal, 100, 300, 50);
        inventoryService.addProduct("Свинина", 30, LocalDate.now().plusDays(3), TemperatureMode.Frozen, 20, 50, 10);
        inventoryService.addProduct("Сыр", 11, LocalDate.now().plusDays(10), TemperatureMode.Chilled, 20, 60, 15);
        inventoryService.addProduct("Картофель", 100, LocalDate.now().plusDays(20), TemperatureMode.Normal, 50, 150, 30);
        inventoryService.addProduct("Лук", 80, LocalDate.now().minusDays(25), TemperatureMode.Normal, 40, 120, 25);
        inventoryService.addProduct("Мороженое", 4, LocalDate.now().minusDays(60), TemperatureMode.Frozen, 10, 40, 5);

        ConsoleUI ui = new ConsoleUI(inventoryService);

        ui.start();
    }
}
