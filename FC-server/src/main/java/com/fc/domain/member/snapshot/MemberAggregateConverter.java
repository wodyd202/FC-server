package com.fc.domain.member.snapshot;

import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.domain.member.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberAggregateConverter implements AttributeConverter<Member, String>{
	private final ObjectMapper objectMapper;
	
	@Override
	public String convertToDatabaseColumn(Member attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Member convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, Member.class);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException();
		}
	}

}
