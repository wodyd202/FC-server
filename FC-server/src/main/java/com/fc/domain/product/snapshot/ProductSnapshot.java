package com.fc.domain.product.snapshot;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fc.core.snapshot.Snapshot;
import com.fc.domain.product.ProductId;
import com.fc.domain.product.Product;

import lombok.Getter;

@Entity
@Getter
@Table(name = "PRODUCT_SNAPSHOT")
public class ProductSnapshot extends Snapshot<Product, ProductId>{
	private static final long serialVersionUID = 1L;

	@Override
	@Convert(converter = ProductAggregateConverter.class)
	public Product getAggregateRoot() {
		return super.getAggregateRoot();
	}
	
	ProductSnapshot() {}
	
	public ProductSnapshot(ProductId identifier, Long version, Product aggregateRoot) {
		super(identifier, version, aggregateRoot);
	}

	public void change(Snapshot<Product, ProductId> snapshot) {
		this.aggregateRoot = snapshot.getAggregateRoot();
		this.version = snapshot.getVersion();
	}
}
