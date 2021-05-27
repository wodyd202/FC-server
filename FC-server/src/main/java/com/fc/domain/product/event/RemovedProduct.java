package com.fc.domain.product.event;

import com.fc.domain.product.ProductId;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RemovedProduct extends AbstractProductEvent{
	private static final long serialVersionUID = 1L;
	public RemovedProduct(ProductId id) {
		this.productId = id;
	}
}
