package org.mycompany.stock.repository;

import org.springframework.data.repository.CrudRepository;
import org.mycompany.stock.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
