package com.fc.domain.product.read;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fc.domain.product.Price;
import com.fc.domain.product.Product.ProductState;
import com.fc.domain.product.ProductCategory;
import com.fc.domain.product.ProductId;
import com.fc.domain.product.ProductTag;
import com.fc.domain.product.ProductTitle;
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

	@JsonManagedReference
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<ProductImage> images;
	
	@Enumerated(EnumType.STRING)
	private ProductState state;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;
}
