package com.fc.query.product.infra;

import java.util.List;
import java.util.Optional;

import com.fc.domain.product.Owner;
import com.fc.domain.product.ProductId;
import com.fc.query.product.model.ProductQuery;
import com.fc.query.product.model.ProductSearch;

public interface ProductRepository {
	List<ProductQuery.ProductList> findAll(Owner owner, ProductSearch dto);

	Optional<ProductQuery.ProductDetail> findDetailByProductId(ProductId productId);
}
