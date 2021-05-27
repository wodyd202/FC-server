package com.fc.product;

import com.fc.command.product.model.ProductCommand.ChangeProductInfo;
import com.fc.command.product.model.ProductCommand.CreateProduct;
import com.fc.core.fileUploader.FileUploader;
import com.fc.core.infra.Validator;
import com.fc.domain.product.Owner;
import com.fc.domain.product.Product;
import com.fc.domain.product.ProductId;

public interface ProductService {
	Product create(
			Validator<CreateProduct> validator,
			FileUploader fileUploader,
			Owner targetOwner,
			CreateProduct command
		);

	Product changeProductInfo(
			Validator<ChangeProductInfo> validator, 
			ProductId targetProductId,
			Owner owner,
			ChangeProductInfo command
		);

	Product deleteProduct(
			ProductId targetProductId,
			Owner owner
		);
}
