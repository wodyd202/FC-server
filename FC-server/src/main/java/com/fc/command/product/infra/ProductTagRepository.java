package com.fc.command.product.infra;

import java.util.List;

import com.fc.domain.product.ProductTag;

public interface ProductTagRepository {
	void save(ProductTag tag);
	boolean existByTagName(String tagName);
	List<ProductTag> findAll();
}
