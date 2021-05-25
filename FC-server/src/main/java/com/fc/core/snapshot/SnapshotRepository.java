package com.fc.core.snapshot;

import java.util.Optional;

import com.fc.core.domain.AggregateRoot;

/**
 * Created by jaceshim on 2017. 3. 9..
 */
@SuppressWarnings("rawtypes")
public interface SnapshotRepository<A extends AggregateRoot, ID> {

	Optional<Snapshot<A, ID>> findLatest(ID id);

	void save(Snapshot<A, ID> snapshot);
}
