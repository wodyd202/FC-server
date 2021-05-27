package com.fc.query.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fc.core.event.EventProjector;
import com.fc.query.store.infra.StoreEventProjector;
import com.fc.query.store.infra.StoreJpaRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class QueryStoreConfig {
	private final StoreJpaRepository storeJpaRepository;

	@Bean
	EventProjector storeEventProjector() {
		return new StoreEventProjector(storeJpaRepository);
	}
}
