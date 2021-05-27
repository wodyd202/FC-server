package com.fc.product;

import com.fc.command.product.model.ProductCommand.CreateProduct;
import com.fc.core.infra.Validator;
import com.fc.domain.product.Owner;
import com.fc.domain.product.Product;

public interface ProductService {
	Product create(
			Validator<CreateProduct> validator, 
			Owner targetOwner,
			CreateProduct command
		);
}
