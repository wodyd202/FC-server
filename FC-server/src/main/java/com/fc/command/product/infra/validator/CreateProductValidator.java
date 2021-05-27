package com.fc.command.product.infra.validator;

import com.fc.command.product.infra.validator.AbstractProductValidator;
import com.fc.command.product.model.ProductCommand.CreateProduct;

public class CreateProductValidator extends AbstractProductValidator<CreateProduct>{

	@Override
	public void validation(CreateProduct target) {
		titleValidation(target.getTitle());
		tagsValidation(target.getTags());
		categoryValidation(target.getCategory());
		priceValidation(target.getPrice());
		sizeValidation(target.getSizes());
		imageValidation(target.getImages());
		mainImageIdxValidation(target.getImages(), target.getMainImageIdx());
	}

}
