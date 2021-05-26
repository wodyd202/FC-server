package com.fc.domain.store;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Component;

@Component
public class HolidayConverter implements AttributeConverter<Set<Holiday>, String> {

	@Override
	public String convertToDatabaseColumn(Set<Holiday> attribute) {
		StringBuilder builder = new StringBuilder();
		attribute.forEach(val -> {
			builder.append(val + ",");
		});
		return builder.toString();
	}

	@Override
	public Set<Holiday> convertToEntityAttribute(String dbData) {
		String[] split = dbData.split(",");
		Set<Holiday> set = new HashSet<>();
		for (int i = 0; i < split.length - 1; i++) {
			set.add(new Holiday(split[i]));
		}
		return set;
	}

}
