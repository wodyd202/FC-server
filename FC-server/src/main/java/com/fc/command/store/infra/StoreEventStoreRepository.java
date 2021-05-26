package com.fc.command.store.infra;

import java.util.List;

import com.fc.domain.store.Owner;
import com.fc.domain.store.event.StoreRawEvent;

public interface StoreEventStoreRepository {
	long countByOwner(Owner identifier);
	List<StoreRawEvent> findByOwner(Owner identifier);
	List<StoreRawEvent> findByOwnerAndVersion(Owner identifier, Long version);
	void save(StoreRawEvent event);
}
