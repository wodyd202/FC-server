package com.fc.command.store.infra;

import com.fc.core.event.EventPublisher;
import com.fc.domain.store.event.StoreRawEvent;

public class StoreEventPublisher implements EventPublisher<StoreRawEvent> {

	@Override
	public void publish(StoreRawEvent rawEvent) {
		
	}

}
