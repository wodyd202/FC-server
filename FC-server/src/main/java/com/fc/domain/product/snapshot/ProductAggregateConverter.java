package com.fc.domain.product.snapshot;

import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.domain.product.read.Product;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductAggregateConverter implements AttributeConverter<Product, String>{
	private final ObjectMapper objectMapper;
	
	@Override
	public String convertToDatabaseColumn(Product attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Product convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, Product.class);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException();
		}
	}

}
