package com.fc.command.store.infra;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fc.core.snapshot.Snapshot;
import com.fc.core.snapshot.SnapshotRepository;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Store;

public class InmemoryStoreSnapshotRepository implements SnapshotRepository<Store,Owner> {
	
	private Map<Owner, Snapshot<Store, Owner>> repo = new HashMap<>();
	
	@Override
	public Optional<Snapshot<Store, Owner>> findLatest(Owner id) {
		return Optional.ofNullable(repo.get(id));
	}

	@Override
	public void save(Snapshot<Store, Owner> snapshot) {
		repo.put(snapshot.getIdentifier(), snapshot);
	}

}
