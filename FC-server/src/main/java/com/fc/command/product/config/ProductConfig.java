package com.fc.command.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.command.product.infra.JdbcProductEventRepository;
import com.fc.command.product.infra.JdbcProductSnapshotRepository;
import com.fc.command.product.infra.ProductEventHandler;
import com.fc.command.product.infra.ProductEventPublisher;
import com.fc.command.product.infra.ProductEventStoreRepository;
import com.fc.command.product.infra.SimpleProductEventStore;
import com.fc.core.event.EventProjector;
import com.fc.core.event.EventPublisher;
import com.fc.core.event.EventStore;
import com.fc.core.snapshot.SnapshotRepository;
import com.fc.domain.product.Product;
import com.fc.domain.product.ProductId;
import com.fc.domain.product.event.ProductRawEvent;

@Configuration
public class ProductConfig {
	@Bean
	ProductEventHandler productEventHandler(ObjectMapper objectMapper, EventProjector productEventProjector) {
		return new ProductEventHandler(productEventStore(objectMapper, productEventProjector), productSnapshotRepository());
	}
	
	@Bean
	SnapshotRepository<Product, ProductId> productSnapshotRepository(){
		return new JdbcProductSnapshotRepository();
	}
	
	@Bean
	EventStore<ProductId> productEventStore(ObjectMapper objectMapper, EventProjector productEventProjector){
		return new SimpleProductEventStore(objectMapper, productEventStoreRepository(), productEventPublisher(), productEventProjector);
	}
	
	@Bean
	ProductEventStoreRepository productEventStoreRepository() {
		return new JdbcProductEventRepository();
	}
	
	@Bean
	EventPublisher<ProductRawEvent> productEventPublisher(){
		return new ProductEventPublisher();
	}
}
