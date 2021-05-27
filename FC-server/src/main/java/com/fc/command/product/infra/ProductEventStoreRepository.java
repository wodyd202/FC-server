package com.fc.command.product.infra;

import java.util.List;

import com.fc.domain.product.ProductId;
import com.fc.domain.product.event.ProductRawEvent;

public interface ProductEventStoreRepository {
	long countByProductId(ProductId identifier);
	List<ProductRawEvent> findByProductId(ProductId identifier);
	List<ProductRawEvent> findByProductIdAndVersion(ProductId identifier, Long version);
	void save(ProductRawEvent event);
}
