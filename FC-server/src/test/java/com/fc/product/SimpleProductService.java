package com.fc.product;

import java.util.UUID;

import com.fc.command.product.infra.ProductEventHandler;
import com.fc.command.product.model.ProductCommand.CreateProduct;
import com.fc.command.store.exception.StoreNotFoundException;
import com.fc.core.infra.Validator;
import com.fc.domain.product.Owner;
import com.fc.domain.product.Product;
import com.fc.domain.product.ProductId;
import com.fc.query.store.infra.StoreRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SimpleProductService implements ProductService {
	private StoreRepository storeRepository;
	private ProductEventHandler productEventHandler;
	
	@Override
	public Product create(Validator<CreateProduct> validator, Owner targetOwner, CreateProduct command) {
		validator.validation(command);
		storeRepository.findByOwner(new com.fc.domain.store.Owner(targetOwner.getEmail()))
			.orElseThrow(()->new StoreNotFoundException("해당 회원의 업체가 존재하지 않습니다."));
		Product product = Product.create(createProductId(),targetOwner, command);
		productEventHandler.save(product);
		return product;
	}

	private ProductId createProductId() {
		return new ProductId(UUID.randomUUID().toString());
	}

}
