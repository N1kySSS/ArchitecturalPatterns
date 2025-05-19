package LayerdArchitecture.infrastructure;

import LayerdArchitecture.domain.IProductRepository;
import LayerdArchitecture.domain.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements IProductRepository {
    private final Map<String, Product> products = new HashMap<>();

    @Override
    public void addProduct(Product product) {
        products.put(product.getName(), product);
    }

    @Override
    public Product getProduct(String name) {
        return products.get(name);
    }

    @Override
    public boolean takeInventory(String name, int amount) {
        Product product = products.get(name);

        if (product != null) {
            return product.getQuantity() == amount;
        }

        throw new IllegalArgumentException("Такого продукта нет.");
    }

    @Override
    public void adjustInventory(String name, int newQuantity) {
        Product product = products.get(name);

        if (product != null) {
            product.addQuantity(newQuantity);
        } else {
            throw new IllegalArgumentException("Такого продукта нет.");
        }

    }

    @Override
    public List<Product> findCriticalStockLevelProducts() {
        return products.values().stream()
                .filter(Product::isCritical)
                .collect(Collectors.toList());
    }

    @Override
    public void removeProduct(String name, int amount) {
        Product product = products.get(name);

        if (product != null) {
            product.reduceQuantity(amount);
        } else {
            throw new IllegalArgumentException("Такого продукта нет.");
        }

    }

    @Override
    public void removeExpiredProducts() {
        products.values().removeIf(Product::isExpired);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }
}
