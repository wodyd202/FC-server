package com.fc.domain.product.event;

import com.fc.domain.product.ProductId;
import com.fc.domain.product.ProductTitle;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
  * @Date : 2021. 5. 27. 
  * @작성자 : LJY
  * @프로그램 설명 : 의류 제목 수정시 발생되는 이벤트
  */
@Getter
@NoArgsConstructor
public class ChangedProductTitle extends AbstractProductEvent{
	private static final long serialVersionUID = 1L;
	private ProductTitle title;
	public ChangedProductTitle(ProductId id, ProductTitle title) {
		this.productId = id;
		this.title = title;
	}
}
