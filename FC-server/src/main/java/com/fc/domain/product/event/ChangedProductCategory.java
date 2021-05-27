package com.fc.domain.product.event;

import com.fc.domain.product.ProductCategory;
import com.fc.domain.product.ProductId;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
  * @Date : 2021. 5. 27. 
  * @작성자 : LJY
  * @프로그램 설명 : 의류 분류 변경시 발생되는 이벤트
  */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangedProductCategory extends AbstractProductEvent{
	private static final long serialVersionUID = 1L;
	private ProductCategory category;
	public ChangedProductCategory(ProductId id, ProductCategory category) {
		this.productId = id;
		this.category = category;
	}
}
