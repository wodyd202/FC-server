package com.fc.product;

import java.util.List;

import com.fc.command.product.exception.InvalidProductException;
import com.fc.command.product.infra.validator.AbstractProductValidator;
import com.fc.command.product.model.ProductCommand.ChangeProductInfo;
import com.fc.domain.product.SizeList.Size;

public class ChangeProductInfoValidator extends AbstractProductValidator<ChangeProductInfo>{

	@Override
	public void validation(ChangeProductInfo target) {
		String title = target.getTitle();
		List<String> tags = target.getTags();
		List<Size> sizes = target.getSizes();
		Integer price = target.getPrice();
		String category = target.getCategory();
		
		isEmptyAll(title,tags,sizes,price,category);
		
		if(title != null) {
			titleValidation(title);
		}
		
		if(tags != null) {
			tagsValidation(tags);
		}
		
		if(sizes != null) {
			sizeValidation(sizes);
		}
		
		if(price != null) {
			priceValidation(price);
		}
		
		if(category != null) {
			categoryValidation(category);
		}
	}

	private void isEmptyAll(String title, List<String> tags, List<Size> sizes, Integer price, String category) {
		if(title == null && tags == null && sizes == null && price == null && category == null) {
			throw new InvalidProductException("적어도 하나 이상의 의류 수정 항목을 입력해주세요.");
		}
	}

}
