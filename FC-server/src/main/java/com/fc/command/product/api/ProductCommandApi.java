package com.fc.command.product.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fc.command.product.ProductService;
import com.fc.command.product.infra.validator.ChangeProductInfoValidator;
import com.fc.command.product.infra.validator.CreateProductValidator;
import com.fc.command.product.model.ProductCommand;
import com.fc.config.security.LoginUser;
import com.fc.core.fileUploader.FileUploader;
import com.fc.domain.member.read.Member;
import com.fc.domain.product.Owner;
import com.fc.domain.product.Product;
import com.fc.domain.product.ProductId;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/product")
@Api(tags = "의류 상태 관련 API")
public class ProductCommandApi {
	private ProductService productService;
	private CreateProductValidator createProductValidator;
	private ChangeProductInfoValidator changeProductInfoValidator;
	private FileUploader fileUploader;
	
	@ApiOperation("의류 등록")
	@PostMapping
	public ResponseEntity<Product> execute(
			ProductCommand.CreateProduct command,
			@ApiIgnore
			@LoginUser Member loginMember
		){
		Product createProduct = productService.create(createProductValidator, fileUploader, Owner.withMember(loginMember), command);
		return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
	}
	
	@ApiOperation("의류 정보 수정")
	@PutMapping("{productId}")
	public ResponseEntity<Product> execute(
			@PathVariable ProductId productId,
			ProductCommand.ChangeProductInfo command,
			@ApiIgnore
			@LoginUser Member loginMember
		){
		Product changeProductInfo = productService.changeProductInfo(changeProductInfoValidator, productId, Owner.withMember(loginMember), command);
		return new ResponseEntity<>(changeProductInfo, HttpStatus.OK);
	}

}
