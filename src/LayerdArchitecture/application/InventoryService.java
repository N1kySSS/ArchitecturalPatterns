package LayerdArchitecture.application;

import LayerdArchitecture.domain.IProductRepository;
import LayerdArchitecture.domain.Product;
import LayerdArchitecture.domain.TemperatureMode;

import java.time.LocalDate;
import java.util.List;

public class InventoryService {
    private final IProductRepository inventory;

    public InventoryService(IProductRepository inventory) {
        this.inventory = inventory;
    }

    public void addProduct(String name, int quantity, LocalDate expiry, TemperatureMode temperatureMode,
                               int minimumStock, int optimalStock, int criticalLevel) {
        Product product = new Product(name, quantity, expiry, temperatureMode,
                minimumStock, optimalStock, criticalLevel);
        inventory.addProduct(product);
    }

    public Product getProduct(String name) {
        return inventory.getProduct(name);
    }

    public boolean checkStock(String name, int amount) {
        return inventory.takeInventory(name, amount);
    }

    public void adjustInventory(String name, int newQuantity) {
        inventory.adjustInventory(name, newQuantity);
    }

    public List<Product> viewCritical() {
        return inventory.findCriticalStockLevelProducts();
    }

    public void useProduct(String name, int amount) {
        inventory.removeProduct(name, amount);
    }

    public void removeExpired() {
        inventory.removeExpiredProducts();
    }

    public List<Product> viewAll() {
        return inventory.getAllProducts();
    }
}
