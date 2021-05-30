package com.fc.query.product.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fc.domain.product.Owner;
import com.fc.domain.product.ProductId;
import com.fc.query.product.model.ProductQuery;
import com.fc.query.product.model.ProductQuery.ProductDetail;
import com.fc.query.product.model.ProductQuery.ProductList;
import com.fc.query.product.model.ProductSearch;
import com.fc.query.product.service.QueryProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/product")
@Api(tags = "의류 조회 관련 API")
public class ProductQueryApi {
	private QueryProductService productService;
	
	@ApiOperation("업체 의류 목록 가져오기")
	@GetMapping("{owner}/product")
	public ResponseEntity<List<ProductQuery.ProductList>> findAll(
			@PathVariable Owner owner, 
			ProductSearch dto
		){
		List<ProductList> findAll = productService.findAll(owner, dto);
		return new ResponseEntity<>(findAll, HttpStatus.OK);
	}

	@ApiOperation("업체 신상 의류 6개 가져오기")
	@GetMapping("{owner}/new")
	public ResponseEntity<List<ProductQuery.ProductList>> findNewProducts(
			@PathVariable Owner owner
		){
		List<ProductList> findNewProducts = productService.findNewProducts(owner);
		return new ResponseEntity<>(findNewProducts, HttpStatus.OK);
	}
	
	@ApiOperation("해당 의류 가져오기")
	@GetMapping("{productId}")
	public ResponseEntity<ProductQuery.ProductDetail> findDetail(
			@PathVariable ProductId productId
		){
		ProductDetail findDetail = productService.findDetailByProductId(productId);
		return new ResponseEntity<>(findDetail, HttpStatus.OK);
	}
}
