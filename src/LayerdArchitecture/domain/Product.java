package LayerdArchitecture.domain;

import java.time.LocalDate;

public class Product {
    private final String name;
    private int quantity;
    private final LocalDate expiryDate;
    private final TemperatureMode temperatureMode;
    private final int minimumStock;
    private final int optimalStock;
    private final int criticalLevel;

    public Product(String name, int quantity, LocalDate expiryDate, TemperatureMode temperatureMode, int minimumStock, int optimalStock, int criticalLevel) {
        this.name = name;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.temperatureMode = temperatureMode;
        this.minimumStock = minimumStock;
        this.optimalStock = optimalStock;
        this.criticalLevel = criticalLevel;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public TemperatureMode getTemperatureMode() {
        return temperatureMode;
    }

    public int getMinimumStock() {
        return minimumStock;
    }

    public int getOptimalStock() {
        return optimalStock;
    }

    public int getCriticalLevel() {
        return criticalLevel;
    }

    public void reduceQuantity(int amount) {
        if (amount > quantity) throw new IllegalArgumentException("Недостаточное количество продукта");
        quantity -= amount;
    }

    public void addQuantity(int amount) {
        quantity += amount;
    }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }

    public boolean isCritical() {
        return quantity <= criticalLevel;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", expiryDate=" + expiryDate +
                ", temperatureMode=" + temperatureMode +
                ", minimumStock=" + minimumStock +
                ", optimalStock=" + optimalStock +
                ", criticalLevel=" + criticalLevel +
                '}';
    }
}
