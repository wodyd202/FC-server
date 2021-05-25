package com.fc.core.event;

/**
 * Created by jaceshim on 2017. 3. 17..
 */
@SuppressWarnings("rawtypes")
public interface EventPublisher<T extends RawEvent> {

	void publish(T rawEvent);
}
