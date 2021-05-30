package com.fc.domain.member;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;

import com.fc.domain.member.StoreOwner;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Embeddable
@AllArgsConstructor
public class InterestStores implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<StoreOwner> stores;

	InterestStores() {}

	public void add(StoreOwner owner) {
		if (this.stores == null) {
			this.stores = new ArrayList<>();
		}
		this.stores.add(owner);
	}

	public void remove(StoreOwner owner) {
		this.stores.remove(owner);
	}
}
