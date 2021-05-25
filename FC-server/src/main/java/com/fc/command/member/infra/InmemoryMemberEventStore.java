package com.fc.command.member.infra;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.core.event.Event;
import com.fc.core.event.EventProjector;
import com.fc.core.event.EventPublisher;
import com.fc.core.event.EventStore;
import com.fc.domain.member.Email;
import com.fc.domain.member.event.MemberRawEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings({"rawtypes","unchecked"})
@RequiredArgsConstructor
public class InmemoryMemberEventStore implements EventStore<Email> {
	private final ObjectMapper objectMapper;
	private final MemberEventStoreRepository eventStoreRepository;
	private final EventPublisher eventPublisher;
	private final EventProjector eventProjector;
	
	@Override
	public void saveEvents(final Email identifier, Long expectedVersion, final List<Event> baseEvents) {
		if(expectedVersion > 0) {
			List<MemberRawEvent> events = eventStoreRepository.findByEmail(identifier);
			Long actualVersion = events.get(events.size() - 1).getVersion();

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
			LocalDateTime now = LocalDateTime.now();
			
			MemberRawEvent rawEvent = MemberRawEvent.builder()
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
	public List<Event<Email>> getEvents(Email identifier) {
		List<MemberRawEvent> findAll = eventStoreRepository.findByEmail(identifier);
		return convertEvent(findAll);
	}

	@Override
	public List<Event<Email>> getAllEvents() {
		List<MemberRawEvent> findAll = eventStoreRepository.findAll();
		return convertEvent(findAll);
	}

	@Override
	public List<Event<Email>> getEventsByAfterVersion(Email identifier, Long version) {
		List<MemberRawEvent> findAll = eventStoreRepository.findByEmailAndVersion(identifier, version);
		return convertEvent(findAll);
	}
	
	private List<Event<Email>> convertEvent(List<MemberRawEvent> rawEvents) {
		return rawEvents.stream().map(rawEvent -> {
			Event<Email> event = null;
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
