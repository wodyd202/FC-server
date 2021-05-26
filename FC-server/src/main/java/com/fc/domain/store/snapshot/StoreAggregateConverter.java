package com.fc.domain.store.snapshot;

import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.domain.store.read.Store;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StoreAggregateConverter implements AttributeConverter<Store, String>{
	private final ObjectMapper objectMapper;
	
	@Override
	public String convertToDatabaseColumn(Store attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Store convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, Store.class);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException();
		}
	}

}