package com.fc.domain.product.event;

import com.fc.domain.product.ProductId;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
  * @Date : 2021. 5. 27. 
  * @작성자 : LJY
  * @프로그램 설명 : 의류 이미지 삭제시 발생되는 이벤트
  */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RemovedProductImage extends AbstractProductEvent{
	private static final long serialVersionUID = 1L;
	private Long imageId;
	public RemovedProductImage(ProductId id, Long imageId) {
		this.productId = id;
		this.imageId = imageId;
	}
}
