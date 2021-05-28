package com.fc.command.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.command.store.infra.JdbcStoreEventRepository;
import com.fc.command.store.infra.JdbcStoreSnapshotRepository;
import com.fc.command.store.infra.SimpleStoreEventStore;
import com.fc.command.store.infra.StoreEventHandler;
import com.fc.command.store.infra.StoreEventPublisher;
import com.fc.command.store.infra.StoreEventStoreRepository;
import com.fc.core.event.EventProjector;
import com.fc.core.event.EventPublisher;
import com.fc.core.event.EventStore;
import com.fc.core.snapshot.SnapshotRepository;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Store;
import com.fc.domain.store.event.StoreRawEvent;

@Configuration
public class StoreConfig {

	@Bean
	StoreEventHandler storeEventHandler(ObjectMapper objectMapper, EventProjector storeEventProjector) {
		return new StoreEventHandler(storeEventStore(objectMapper, storeEventProjector), storeSnapshotRepository());
	}

	@Bean
	SnapshotRepository<Store, Owner> storeSnapshotRepository() {
		return new JdbcStoreSnapshotRepository();
	}

	@Bean
	EventStore<Owner> storeEventStore(ObjectMapper objectMapper, EventProjector storeEventProjector) {
		return new SimpleStoreEventStore(objectMapper, storeEventStoreRepository(), storeEventPublisher(),
				storeEventProjector);
	}

	@Bean
	StoreEventStoreRepository storeEventStoreRepository() {
		return new JdbcStoreEventRepository();
	}

	@Bean
	EventPublisher<StoreRawEvent> storeEventPublisher() {
		return new StoreEventPublisher();
	}
}
