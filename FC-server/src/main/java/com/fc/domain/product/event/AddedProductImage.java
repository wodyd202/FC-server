package com.fc.domain.product.event;

import com.fc.domain.product.ProductId;
import com.fc.domain.product.read.ProductImage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Date : 2021. 5. 27.
 * @작성자 : LJY
 * @프로그램 설명 : 의류 이미지 등록시 발생되는 이벤트
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddedProductImage extends AbstractProductEvent {
	private static final long serialVersionUID = 1L;
	private ProductImage image;
	public AddedProductImage(ProductId id, ProductImage image) {
		this.productId = id;
		this.image = image;
	}
}
