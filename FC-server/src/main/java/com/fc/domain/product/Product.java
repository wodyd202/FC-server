package com.fc.domain.product;

import java.util.Date;
import java.util.List;

import com.fc.command.product.exception.InvalidProductException;
import com.fc.command.product.model.ProductCommand.CreateProduct;
import com.fc.core.domain.AggregateRoot;
import com.fc.domain.product.SizeList.Size;
import com.fc.domain.product.event.ChangedProductCategory;
import com.fc.domain.product.event.ChangedProductPrice;
import com.fc.domain.product.event.ChangedProductSize;
import com.fc.domain.product.event.ChangedProductTags;
import com.fc.domain.product.event.ChangedProductTitle;
import com.fc.domain.product.event.CreatedProduct;
import com.fc.domain.product.event.RemovedProduct;

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
	
	public static Product create(ProductId id, Owner targetOwner, CreateProduct command, List<ProductImage> imageList) {
		return Product.builder()
				.id(id)
				.title(command.getTitle())
				.tags(command.getTags())
				.category(command.getCategory())
				.price(command.getPrice())
				.images(imageList)
				.size(command.getSizes())
				.owner(targetOwner)
				.build();
	}
	
	public void changeProductTitle(String title) {
		this.title = new ProductTitle(title);
		applyChange(new ChangedProductTitle(this.id, this.title));
	}

	public void changeProductTags(List<String> tags) {
		this.tags = new ProductTags(tags);
		applyChange(new ChangedProductTags(this.id, this.tags));
	}

	public void changeProductSize(List<Size> sizes) {
		this.size = new SizeList(sizes);
		applyChange(new ChangedProductSize(this.id, this.size));
	}

	public void changePrice(Integer price) {
		this.price = new Price(price);
		applyChange(new ChangedProductPrice(this.id, this.price));
	}

	public void changeCategory(String category) {
		this.category = new ProductCategory(category);
		applyChange(new ChangedProductCategory(this.id, this.category));
	}
	
	public void delete() {
		if(isDelete()) {
			throw new InvalidProductException("이미 판매 중단된 의류입니다.");
		}
		this.state = ProductState.NOT_SELL;
		applyChange(new RemovedProduct(this.id));
	}
	
	public boolean isDelete() {
		return this.state == ProductState.NOT_SELL;
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
	
	protected void apply(ChangedProductTitle event) {
		this.title = event.getTitle();
	}
	
	protected void apply(ChangedProductTags event) {
		this.tags = event.getTags();
	}
	
	protected void apply(ChangedProductSize event) {
		this.size = event.getSize();
	}

	protected void apply(ChangedProductPrice event) {
		this.price = event.getPrice();
	}
	
	protected void apply(ChangedProductCategory event) {
		this.category = event.getCategory();
	}
	
	protected void apply(RemovedProduct event) {
		this.state = ProductState.NOT_SELL;
	}

	
}
