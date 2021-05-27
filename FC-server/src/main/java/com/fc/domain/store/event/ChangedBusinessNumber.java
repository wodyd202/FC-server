package com.fc.domain.store.event;

import com.fc.domain.store.Owner;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangedBusinessNumber extends AbstractStoreEvent {
	private static final long serialVersionUID = 1L;
	private String businessNumber;
	
	public ChangedBusinessNumber(Owner owner, String businessNumber) {
		this.owner = owner;
		this.businessNumber = businessNumber;
	}
}
