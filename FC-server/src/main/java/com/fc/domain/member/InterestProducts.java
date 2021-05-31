package com.fc.domain.member;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Embeddable
@AllArgsConstructor
public class InterestProducts implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<StoreProductId> products;

	InterestProducts() {
		this.products = new ArrayList<>();
	}

	public void add(StoreProductId product) {
		this.products.add(product);
	}

	public void remove(StoreProductId product) {
		this.products.remove(product);
	}
}
