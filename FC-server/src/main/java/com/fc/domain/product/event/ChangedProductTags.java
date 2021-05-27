package com.fc.domain.product.event;

import com.fc.domain.product.ProductId;
import com.fc.domain.product.ProductTags;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
  * @Date : 2021. 5. 27. 
  * @작성자 : LJY
  * @프로그램 설명 : 의류 태그 변경시 발생되는 이벤트
  */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangedProductTags extends AbstractProductEvent{
	private static final long serialVersionUID = 1L;
	private ProductTags tags;
	public ChangedProductTags(ProductId id, ProductTags tags) {
		this.productId = id;
		this.tags = tags;
	}
}
