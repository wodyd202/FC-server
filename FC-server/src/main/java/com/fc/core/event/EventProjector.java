package com.fc.core.event;

/**
 * Created by jaceshim on 2017. 4. 19..
 */
@SuppressWarnings("rawtypes")
public interface EventProjector {
	void handle(Event event);
}
