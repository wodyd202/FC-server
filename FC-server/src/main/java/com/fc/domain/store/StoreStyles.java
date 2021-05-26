package com.fc.domain.store;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreStyles {
	Set<StoreStyle> styles;
	
	public StoreStyles(List<String> styles) {
		this.styles = styles.stream().map(StoreStyle::new).collect(Collectors.toSet());
	}
}
