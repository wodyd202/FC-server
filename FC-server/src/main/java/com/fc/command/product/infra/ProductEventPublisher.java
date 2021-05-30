package com.fc.command.product.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import com.fc.core.event.EventPublisher;
import com.fc.domain.product.event.ProductRawEvent;

public class ProductEventPublisher implements EventPublisher<ProductRawEvent> {
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Override
	public void publish(ProductRawEvent rawEvent) {
		publisher.publishEvent(rawEvent);	
	}

}
