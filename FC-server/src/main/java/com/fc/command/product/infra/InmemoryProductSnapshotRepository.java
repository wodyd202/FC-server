package com.fc.command.product.infra;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fc.core.snapshot.Snapshot;
import com.fc.core.snapshot.SnapshotRepository;
import com.fc.domain.product.Product;
import com.fc.domain.product.ProductId;

public class InmemoryProductSnapshotRepository implements SnapshotRepository<Product,ProductId> {
	
	private Map<ProductId, Snapshot<Product, ProductId>> repo = new HashMap<>();
	
	@Override
	public Optional<Snapshot<Product, ProductId>> findLatest(ProductId id) {
		return Optional.ofNullable(repo.get(id));
	}

	@Override
	public void save(Snapshot<Product, ProductId> snapshot) {
		repo.put(snapshot.getIdentifier(), snapshot);
	}

}
