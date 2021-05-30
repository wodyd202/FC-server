package com.fc.query.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fc.command.product.exception.ProductNotFoundException;
import com.fc.domain.product.Owner;
import com.fc.domain.product.ProductId;
import com.fc.query.product.infra.ProductRepository;
import com.fc.query.product.model.ProductQuery;
import com.fc.query.product.model.ProductSearch;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@Service
@AllArgsConstructor
public class QueryProductService {
	private ProductRepository productRepository;
	
	public List<ProductQuery.ProductList> findAll(Owner owner, ProductSearch dto){
		return productRepository.findAll(owner, dto);
	}
	
	public ProductQuery.ProductDetail findDetailByProductId(ProductId productId){
		return productRepository.findDetailByProductId(productId)
				.orElseThrow(()->new ProductNotFoundException("해당 의류가 존재하지 않습니다."));
	}
}
