package HexagonalArchitecture.domain.port.secondary;


import HexagonalArchitecture.domain.model.Product;

import java.util.List;

public interface ProductRepository {
    Product findById(String id);

    List<Product> findAll();
}
