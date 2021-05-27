package com.fc.domain.product.event;

import com.fc.domain.product.Price;
import com.fc.domain.product.ProductId;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangedProductPrice extends AbstractProductEvent {
	private static final long serialVersionUID = 1L;
	private Price price;
	public ChangedProductPrice(ProductId id, Price price) {
		this.productId = id;
		this.price = price;
	}
}
