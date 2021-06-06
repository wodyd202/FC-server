package com.fc.command.product.infra;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fc.domain.product.ProductTag;

@Repository
public class InmemoryProductTagRepository implements ProductTagRepository{

	private List<ProductTag> repo = new ArrayList<>();

	@Override
	public boolean existByTagName(String tagName) {
		return repo.contains(new ProductTag(tagName));
	}

	@Override
	public void save(ProductTag tag) {
		repo.add(tag);
	}

	@Override
	public List<ProductTag> findAll() {
		return repo;
	}

}
