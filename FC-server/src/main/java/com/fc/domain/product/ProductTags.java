package com.fc.domain.product;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductTags {
	private Set<ProductTag> tags;

	public ProductTags(List<String> tags) {
		this.tags = tags.stream().map(ProductTag::new).collect(Collectors.toSet());
	}
}
