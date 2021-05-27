package com.fc.domain.product.event;

import java.io.Serializable;

import com.fc.core.event.Event;
import com.fc.domain.product.ProductId;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AbstractProductEvent implements Event<ProductId>, Serializable {
	private static final long serialVersionUID = 1L;
	protected ProductId productId;
	
	@Override
	public ProductId getIdentifier() {
		return this.productId;
	}

}
