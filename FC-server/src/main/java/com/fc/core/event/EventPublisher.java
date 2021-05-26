package com.fc.core.event;

@SuppressWarnings("rawtypes")
public interface EventPublisher<T extends RawEvent> {

	void publish(T rawEvent);
}
