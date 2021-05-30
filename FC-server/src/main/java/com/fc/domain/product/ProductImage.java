package com.fc.domain.product;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ProductImage implements Serializable {
	private static final long serialVersionUID = 1L;
	public enum ProductImageType { MAIN, SUB }

	private String imageId;
	private String path;

	@Enumerated(EnumType.STRING)
	private ProductImageType type;

}
