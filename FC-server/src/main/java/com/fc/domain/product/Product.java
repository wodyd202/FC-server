package com.fc.domain.product;

import java.util.Date;
import java.util.List;

import com.fc.command.product.model.ProductCommand.CreateProduct;
import com.fc.core.domain.AggregateRoot;
import com.fc.domain.product.SizeList.Size;
import com.fc.domain.product.event.CreatedProduct;

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
	private Owner owner;
	
	@Builder
	private Product(
			ProductId id,
			String title,
			List<String> tags, 
			String category, 
			int price, 
			List<Size> size, 
			List<ProductImage> images,
			Owner owner
			) {
		this.id = id;
		this.title = new ProductTitle(title);
		this.tags = new ProductTags(tags);
		this.category = new ProductCategory(category);
		this.price = new Price(price);
		this.images = new Images(images);
		this.size = new SizeList(size);
		this.owner = owner;
		this.state = ProductState.SELL;
		this.createDateTime = new Date();
		applyChange(new CreatedProduct(this.id, this.title, this.tags, this.category, this.price, this.size, this.images, createDateTime, owner));
	}
	
	public static Product create(ProductId id, Owner targetOwner, CreateProduct command) {
		return Product.builder()
				.id(id)
				.title(command.getTitle())
				.tags(command.getTags())
				.category(command.getCategory())
				.price(command.getPrice())
				.images(null)
				.size(command.getSizes())
				.owner(targetOwner)
				.build();
	}
	
	protected void apply(CreatedProduct event) {
		this.title = event.getTitle();
		this.tags = event.getProductTags();
		this.category = event.getCategory();
		this.price = event.getPrice();
		this.size = event.getSizes();
		this.images = event.getImages();
		this.createDateTime = event.getCreateDateTime();
		this.owner = event.getOwner();
	}
}
