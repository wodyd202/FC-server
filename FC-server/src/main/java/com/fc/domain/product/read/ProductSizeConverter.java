package com.fc.domain.product.read;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Component;

import com.fc.domain.product.SizeList.Size;

@Component
public class ProductSizeConverter implements AttributeConverter<Set<Size>, String> {

	@Override
	public String convertToDatabaseColumn(Set<Size> attribute) {
		StringBuilder builder = new StringBuilder();
		attribute.forEach(val -> {
			builder.append(val + ",");
		});
		return builder.toString();
	}

	@Override
	public Set<Size> convertToEntityAttribute(String dbData) {
		String[] split = dbData.split(",");
		Set<Size> sizes = new HashSet<>();
		for(String value : split) {
			sizes.add(Size.valueOf(value));
		}
		return sizes;
	}

}
