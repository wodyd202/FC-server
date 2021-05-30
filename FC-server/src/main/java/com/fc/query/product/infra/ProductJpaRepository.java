package com.fc.query.product.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fc.domain.product.ProductId;
import com.fc.domain.product.read.Product;

public interface ProductJpaRepository extends JpaRepository<Product, ProductId>{

}
