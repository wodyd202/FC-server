package com.fc.query.product.model;

import java.util.List;

import com.fc.domain.product.ProductImage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductQuery {
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ProductList {
		private String productId;
		private String category;
		private String title;
		private String mainImagePath;
		private String size;
		private int price;
		private boolean interest;
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ProductDetail {
		private String productId;
		private String title;
		private String tags;
		private int price;
		private String size;
		private List<ProductImage> images;
		private boolean interest;
		
		public void addImages(List<ProductImage> images) {
			this.images = images;
		}
	}
}
