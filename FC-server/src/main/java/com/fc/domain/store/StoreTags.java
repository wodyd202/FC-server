package com.fc.domain.store;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreTags {
	private Set<StoreTag> tags;
	
	public StoreTags(List<String> tags) {
		this.tags = tags.stream().map(StoreTag::new).collect(Collectors.toSet());
	}
}
