package com.fc.domain.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Holiday {
	private String value;
	
	@Override
	public String toString() {
		return this.value;
	}
}
