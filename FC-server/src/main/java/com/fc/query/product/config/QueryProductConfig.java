package com.fc.query.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fc.core.event.EventProjector;
import com.fc.query.product.infra.ProductEventProjector;
import com.fc.query.product.infra.ProductJpaRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class QueryProductConfig {
	private final ProductJpaRepository productJpaRepository;
	@Bean
	EventProjector productEventProjector() {
		return new ProductEventProjector(productJpaRepository);
	}
}
