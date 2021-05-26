package com.fc.command.store.infra;

import com.fc.core.event.AbstractEventHandler;
import com.fc.core.event.EventStore;
import com.fc.core.snapshot.SnapshotRepository;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Store;

@SuppressWarnings("rawtypes")
public class StoreEventHandler extends AbstractEventHandler<Store, Owner>{

	public StoreEventHandler(EventStore eventStore, SnapshotRepository snapshotRepository) {
		super(eventStore, snapshotRepository);
	}

}
