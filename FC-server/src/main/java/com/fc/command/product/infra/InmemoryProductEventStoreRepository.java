package com.fc.command.product.infra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fc.domain.product.ProductId;
import com.fc.domain.product.event.ProductRawEvent;

public class InmemoryProductEventStoreRepository implements ProductEventStoreRepository {
	private final Map<ProductId, List<ProductRawEvent>> repo = new HashMap<>();

	@Override
	public long countByProductId(ProductId identifier) {
		List<ProductRawEvent> list = repo.get(identifier);
		return list == null ? 0 : list.size();
	}

	@Override
	public List<ProductRawEvent> findByProductId(ProductId identifier) {
		List<ProductRawEvent> list = repo.get(identifier);
		return list == null ? new ArrayList<>() : list;
	}

	@Override
	public List<ProductRawEvent> findByProductIdAndVersion(ProductId identifier, Long version) {
		List<ProductRawEvent> persistEvents = repo.get(identifier);
		List<ProductRawEvent> events = new ArrayList<>();

		if (persistEvents == null) {
			return events;
		}
		persistEvents.forEach(c -> {
			if (c.getVersion() > version) {
				events.add(c);
			}
		});

		return events;
	}

	@Override
	public void save(ProductRawEvent event) {
		ProductId identifier = event.getIdentifier();
		List<ProductRawEvent> list = repo.get(identifier);
		if (list == null) {
			repo.put(identifier, new ArrayList<>());
			list = repo.get(identifier);
		}
		list.add(event);
	}

}
