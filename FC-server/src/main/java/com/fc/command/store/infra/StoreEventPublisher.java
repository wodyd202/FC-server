package com.fc.command.store.infra;

import org.springframework.context.ApplicationEventPublisher;

import com.fc.core.event.EventPublisher;
import com.fc.domain.store.event.StoreRawEvent;

public class StoreEventPublisher implements EventPublisher<StoreRawEvent> {
	private ApplicationEventPublisher publisher;
	
	@Override
	public void publish(StoreRawEvent rawEvent) {
		publisher.publishEvent(rawEvent);	
	}

}
