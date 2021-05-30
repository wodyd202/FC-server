package com.fc.domain.product.read;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Component;

import com.fc.domain.product.ProductTag;

@Component
public class ProductTagsConverter implements AttributeConverter<Set<ProductTag>, String>{

	@Override
	public String convertToDatabaseColumn(Set<ProductTag> attribute) {
		StringBuilder builder = new StringBuilder();
		attribute.forEach(tag -> {
			builder.append(tag.getName() + ",");
		});
		return builder.toString();
	}

	@Override
	public Set<ProductTag> convertToEntityAttribute(String dbData) {
		String[] split = dbData.split(",");
		Set<ProductTag> tags = new HashSet<>();
		for(String value : split) {
			tags.add(new ProductTag(value));
		}
		return tags;
	}

}
