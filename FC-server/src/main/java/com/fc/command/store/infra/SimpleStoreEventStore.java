package com.fc.command.store.infra;

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
import com.fc.domain.store.Owner;
import com.fc.domain.store.event.StoreRawEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings({"rawtypes","unchecked"})
@RequiredArgsConstructor
public class SimpleStoreEventStore implements EventStore<Owner> {
	private final ObjectMapper objectMapper;
	private final StoreEventStoreRepository eventStoreRepository;
	private final EventPublisher eventPublisher;
	private final EventProjector eventProjector;
	
	@Override
	public long countEvents(Owner identifier) {
		return eventStoreRepository.countByOwner(identifier);
	}
	
	@Override
	public void saveEvents(final Owner identifier, Long expectedVersion, final List<Event> baseEvents) {
		if(expectedVersion > 0) {
			List<StoreRawEvent> events = eventStoreRepository.findByOwner(identifier);
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
			
			StoreRawEvent rawEvent = StoreRawEvent.builder()
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
	public List<Event<Owner>> getEvents(Owner identifier) {
		List<StoreRawEvent> findAll = eventStoreRepository.findByOwner(identifier);
		return convertEvent(findAll);
	}

	@Override
	public List<Event<Owner>> getEventsByAfterVersion(Owner identifier, Long version) {
		List<StoreRawEvent> findAll = eventStoreRepository.findByOwnerAndVersion(identifier, version);
		return convertEvent(findAll);
	}
	
	private List<Event<Owner>> convertEvent(List<StoreRawEvent> rawEvents) {
		return rawEvents.stream().map(rawEvent -> {
			Event<Owner> event = null;
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
