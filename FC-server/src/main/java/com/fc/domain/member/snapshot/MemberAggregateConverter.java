package com.fc.domain.member.snapshot;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.domain.member.Member;

public class MemberAggregateConverter implements AttributeConverter<Member, String>{

	@Override
	public String convertToDatabaseColumn(Member attribute) {
		try {
			return new ObjectMapper().writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Member convertToEntityAttribute(String dbData) {
		try {
			return new ObjectMapper().readValue(dbData, Member.class);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException();
		}
	}

}
