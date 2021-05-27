package com.fc.command.product.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fc.domain.product.SizeList.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductCommand {
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class CreateProduct {
		 private String title;
		 private List<String> tags;
		 private String category;
		 private int price;
		 private List<Size> sizes;
		 private List<MultipartFile> images;
		 private int mainImageIdx;
	}
}
