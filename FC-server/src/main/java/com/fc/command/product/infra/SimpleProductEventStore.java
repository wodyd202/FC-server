package com.fc.command.product.infra;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.core.event.Event;
import com.fc.core.event.EventProjector;
import com.fc.core.event.EventPublisher;
import com.fc.core.event.EventStore;
import com.fc.domain.product.ProductId;
import com.fc.domain.product.event.ProductRawEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings({"rawtypes","unchecked"})
@RequiredArgsConstructor
public class SimpleProductEventStore implements EventStore<ProductId> {
	private final ObjectMapper objectMapper;
	private final ProductEventStoreRepository eventStoreRepository;
	private final EventPublisher eventPublisher;
	private final EventProjector eventProjector;
	
	@Override
	public long countEvents(ProductId identifier) {
		return eventStoreRepository.countByProductId(identifier);
	}
	
	@Override
	public void saveEvents(final ProductId identifier, Long expectedVersion, final List<Event> baseEvents) {
		if(expectedVersion > 0) {
			List<ProductRawEvent> events = eventStoreRepository.findByProductId(identifier);
			Long actualVersion = events.get(events.size() - 1).getVersion() - 1;

			if(expectedVersion.equals(actualVersion)) {
				String exceptionMessage = String.format("Unmatched Version : expected: {}, actual: {}", expectedVersion, actualVersion);
				throw new IllegalStateException(exceptionMessage);
			}
		}
		for(Event event : baseEvents) {
			String type = event.getClass().getName();
			String payload = null;
			try {
				objectMapper.setVisibility(PropertyAccessor.FIELD,Visibility.ANY);
				payload = objectMapper.writeValueAsString(event);
			}catch (Exception e) {}
			expectedVersion = increaseVersion(expectedVersion);
			Date now = new Date();
			
			ProductRawEvent rawEvent = ProductRawEvent.builder()
					.identifiier(identifier)
					.type(type)
					.version(expectedVersion)
					.payload(payload)
					.createDateTime(now)
					.build();
			
			eventStoreRepository.save(rawEvent);
			eventPublisher.publish(rawEvent);
			eventProjector.handle(event);
		}
	}

	private Long increaseVersion(Long expectedVersion) {
		return ++expectedVersion;
	}
	
	@Override
	public List<Event<ProductId>> getEvents(ProductId identifier) {
		List<ProductRawEvent> findAll = eventStoreRepository.findByProductId(identifier);
		return convertEvent(findAll);
	}

	@Override
	public List<Event<ProductId>> getEventsByAfterVersion(ProductId identifier, Long version) {
		List<ProductRawEvent> findAll = eventStoreRepository.findByProductIdAndVersion(identifier, version);
		return convertEvent(findAll);
	}
	
	private List<Event<ProductId>> convertEvent(List<ProductRawEvent> rawEvents) {
		return rawEvents.stream().map(rawEvent -> {
			Event<ProductId> event = null;
			try {
				log.debug("-> event info : {}", rawEvent.toString());
				event = (Event) objectMapper.readValue(rawEvent.getPayload(), Class.forName(rawEvent.getType()));
			} catch (IOException | ClassNotFoundException e) {
				String exceptionMessage = String.format("Event Object Convert Error : {} {}", rawEvent.getIdentifier(), rawEvent.getType(),
					rawEvent.getPayload());
				log.error(exceptionMessage, e);
			}
			return event;
		}).collect(Collectors.toList());
	}


}
