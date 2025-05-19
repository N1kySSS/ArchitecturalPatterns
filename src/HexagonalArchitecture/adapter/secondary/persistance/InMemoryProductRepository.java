package HexagonalArchitecture.adapter.secondary.persistance;


import HexagonalArchitecture.domain.model.Product;
import HexagonalArchitecture.domain.port.secondary.ProductRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<String, Product> products = new HashMap<>();

    public InMemoryProductRepository() {
        // Инициализация тестовыми данными (Прогноз потребностей)
        // Имитация просроченности некоторых продуктов
        addProduct(new Product("P1", "Помидоры", 120.0, LocalDate.now().plusDays(5)));
        addProduct(new Product("P2", "Куриное филе", 300.0, LocalDate.now().minusDays(13)));
        addProduct(new Product("P3", "Молоко", 90.0, LocalDate.now().plusDays(7)));
        addProduct(new Product("P4", "Яйца", 85.0, LocalDate.now().plusDays(10)));
        addProduct(new Product("P5", "Картофель", 50.0, LocalDate.now().plusDays(20)));
        addProduct(new Product("P6", "Сыр", 250.0, LocalDate.now().plusDays(15)));
        addProduct(new Product("P7", "Лосось", 600.0, LocalDate.now().minusDays(5)));
    }

    private void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public Product findById(String id) {
        return products.get(id);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
}
