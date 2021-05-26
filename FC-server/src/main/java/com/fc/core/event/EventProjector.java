package com.fc.core.event;

@SuppressWarnings("rawtypes")
public interface EventProjector {
	void handle(Event event);
}
