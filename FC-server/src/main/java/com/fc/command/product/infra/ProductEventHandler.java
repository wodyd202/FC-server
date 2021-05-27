package com.fc.command.product.infra;

import com.fc.core.event.AbstractEventHandler;
import com.fc.core.event.EventStore;
import com.fc.core.snapshot.SnapshotRepository;
import com.fc.domain.product.Product;
import com.fc.domain.product.ProductId;

@SuppressWarnings("rawtypes")
public class ProductEventHandler extends AbstractEventHandler<Product, ProductId>{

	public ProductEventHandler(EventStore eventStore, SnapshotRepository snapshotRepository) {
		super(eventStore, snapshotRepository);
	}

}
