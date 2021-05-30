package com.fc.domain.product.read;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

import com.fc.domain.product.Owner;
import com.fc.domain.product.Price;
import com.fc.domain.product.Product.ProductState;
import com.fc.domain.product.ProductCategory;
import com.fc.domain.product.ProductId;
import com.fc.domain.product.ProductImage;
import com.fc.domain.product.ProductTag;
import com.fc.domain.product.ProductTags;
import com.fc.domain.product.ProductTitle;
import com.fc.domain.product.SizeList;
import com.fc.domain.product.SizeList.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCT")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Product {
	
	@EmbeddedId
	@AttributeOverride(column = @Column(name = "product_id"), name = "id")
	private ProductId id;
	
	@Embedded
	private ProductTitle title;
	
	@Convert(converter = ProductTagsConverter.class)
	private Set<ProductTag> tags;
	
	@Embedded
	private ProductCategory category;
	
	@Embedded
	private Price price;

	@Convert(converter = ProductSizeConverter.class)
	private Set<Size> sizes;
	
	@ElementCollection
	@CollectionTable(name = "PRODUCT_IMAGES", joinColumns = @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "product_id"))
	private List<ProductImage> images;
	
	@Enumerated(EnumType.STRING)
	private ProductState state;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	@Embedded
	private Owner owner;

	public void addedProductImage(ProductImage image) {
		this.images.add(image);
	}

	public void changeProductCategory(ProductCategory category) {
		this.category = category;
	}

	public void remove() {
		this.state = ProductState.NOT_SELL;
	}

	public void changeTitle(ProductTitle title) {
		this.title = title;
	}

	public void changeTags(ProductTags tags) {
		this.tags = tags.getTags();
	}

	public void changeSize(SizeList size) {
		this.sizes = size.getSizes();
	}

	public void changePrice(Price price) {
		this.price = price;
	}
}
