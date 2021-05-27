package com.fc.domain.product.event;

import com.fc.domain.product.ProductId;
import com.fc.domain.product.SizeList;
import com.fc.domain.product.event.AbstractProductEvent;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangedProductSize extends AbstractProductEvent{
	private static final long serialVersionUID = 1L;
	private SizeList size;
	public ChangedProductSize(ProductId id, SizeList size) {
		this.productId = id;
		this.size = size;
	}
}
