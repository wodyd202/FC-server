package com.fc.core.event;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface EventStore<ID> {
	void saveEvents(ID identifier, Long expectedVersion, List<Event> baseEvents);
	long countEvents(ID identifier);
	List<Event<ID>> getEvents(ID identifier);
	List<Event<ID>> getEventsByAfterVersion(ID identifier, Long version);
}
