package com.fc.domain.product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SizeList {
	public enum Size { XS, S, M, L, XL, XXL, FREE }
	Set<Size> sizes;

	public SizeList(List<Size> size) {
		this.sizes = new HashSet<>(size);
	}
}
