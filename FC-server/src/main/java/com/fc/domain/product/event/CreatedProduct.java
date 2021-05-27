package com.fc.domain.product.event;

import java.util.Date;

import com.fc.domain.product.Images;
import com.fc.domain.product.Owner;
import com.fc.domain.product.Price;
import com.fc.domain.product.ProductCategory;
import com.fc.domain.product.ProductId;
import com.fc.domain.product.ProductTags;
import com.fc.domain.product.ProductTitle;
import com.fc.domain.product.SizeList;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Date : 2021. 5. 27.
 * @작성자 : LJY
 * @프로그램 설명 : 의류 등록 시 발생되는 이벤트
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatedProduct extends AbstractProductEvent {
	private static final long serialVersionUID = 1L;
	private ProductTitle title;
	private ProductTags productTags;
	private ProductCategory category;
	private Price price;
	private SizeList sizes;
	private Images images;
	private Date createDateTime;
	private Owner owner;
	
	public CreatedProduct(
			ProductId id, 
			ProductTitle title,
			ProductTags productTags,
			ProductCategory category,
			Price price,
			SizeList sizes,
			Images images,
			Date createDateTime,
			Owner owner
		) {
		this.productId = id;
		this.title = title;
		this.productTags = productTags;
		this.category = category;
		this.price = price;
		this.sizes = sizes;
		this.images = images;
		this.createDateTime = createDateTime;
		this.owner = owner;
	}
}
