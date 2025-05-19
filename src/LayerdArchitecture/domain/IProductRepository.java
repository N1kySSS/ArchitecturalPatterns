package LayerdArchitecture.domain;

import java.util.List;

public interface IProductRepository {

    void addProduct(Product product);

    Product getProduct(String name);

    List<Product> getAllProducts();

    void removeProduct(String name, int amount);

    void removeExpiredProducts();

    void adjustInventory(String name, int amount);

    boolean takeInventory(String name, int amount);

    List<Product> findCriticalStockLevelProducts();
}
