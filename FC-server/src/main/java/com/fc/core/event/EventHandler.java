package com.fc.core.event;

import java.util.Optional;

import com.fc.core.domain.AggregateRoot;

@SuppressWarnings("rawtypes")
public interface EventHandler<A extends AggregateRoot, ID> {
	void save(A aggregate);
	Optional<A> find(ID identifier);
}
