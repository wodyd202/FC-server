package com.fc.domain.product;

import java.util.Date;

import com.fc.core.domain.AggregateRoot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends AggregateRoot<ProductId>{
	public enum ProductState { SELL, NOT_SELL }
	private static final long serialVersionUID = 1L;
	private ProductId id;
	private ProductTitle title;
	private ProductTags tags;
	private ProductCategory category;
	private Price price;
	private SizeList size;
	private Images images;
	private ProductState state;
	private Date createDateTime;
}
