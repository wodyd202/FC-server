package com.fc.command.product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fc.command.product.exception.InvalidProductException;
import com.fc.command.product.exception.ProductNotFoundException;
import com.fc.command.product.infra.ProductEventHandler;
import com.fc.command.product.model.ProductCommand.ChangeProductInfo;
import com.fc.command.product.model.ProductCommand.CreateProduct;
import com.fc.command.store.exception.StoreNotFoundException;
import com.fc.core.fileUploader.FileUploader;
import com.fc.core.infra.Validator;
import com.fc.domain.product.Owner;
import com.fc.domain.product.Product;
import com.fc.domain.product.ProductId;
import com.fc.domain.product.ProductImage;
import com.fc.domain.product.ProductImage.ProductImageType;
import com.fc.domain.product.SizeList.Size;
import com.fc.query.store.infra.StoreRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SimpleProductService implements ProductService {
	private StoreRepository storeRepository;
	private ProductEventHandler productEventHandler;
	
	@Override
	public Product create(
			Validator<CreateProduct> validator, 
			FileUploader fileUploader,
			Owner targetOwner, 
			CreateProduct command
		) {
		validator.validation(command);
		storeRepository.findByOwner(new com.fc.domain.store.Owner(targetOwner.getEmail()))
			.orElseThrow(()->new StoreNotFoundException("해당 회원의 업체가 존재하지 않습니다."));
		List<ProductImage> imageList = new ArrayList<>();
		
		List<MultipartFile> images = command.getImages();
		int imageCount = images.size();
		
		// 받아온 이미지 파일을 저장후 저장할 객체로 변환
		for(int i = 0;i<imageCount;i++) {
			MultipartFile file = images.get(i);
			String saveFileName = UUID.randomUUID() + getFileExtention(file);
			fileUploader.uploadFile(file, saveFileName);
			
			if(command.getMainImageIdx() == i) {
				imageList.add(new ProductImage(saveFileName, ProductImageType.MAIN));
			}else {
				imageList.add(new ProductImage(saveFileName, ProductImageType.SUB));
			}
		}
		
		Product product = Product.create(createProductId(),targetOwner, command, imageList);
		productEventHandler.save(product);
		return product;
	}
	
	private String getFileExtention(MultipartFile file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		return name.substring(lastIndexOf, name.length()).toUpperCase();
	}
	
	private ProductId createProductId() {
		return new ProductId(UUID.randomUUID().toString());
	}
	@Override
	public Product changeProductInfo(
			Validator<ChangeProductInfo> validator, 
			ProductId targetProductId, 
			Owner owner,
			ChangeProductInfo command
		) {
		validator.validation(command);
		Product product = productEventHandler.find(targetProductId)
				.orElseThrow(()->new ProductNotFoundException("해당 의류가 존재하지 않습니다."));
		if(!owner.isMyProduct(product)) {
			throw new InvalidProductException("자신이 등록한 의류만 수정할 수 있습니다.");
		}
		if(product.isDelete()) {
			throw new InvalidProductException("판매중인 의류만 수정할 수 있습니다.");
		}
		
		String title = command.getTitle();
		List<String> tags = command.getTags();
		List<Size> sizes = command.getSizes();
		Integer price = command.getPrice();
		String category = command.getCategory();
		
		if(title != null) {
			product.changeProductTitle(title);
		}
		
		if(tags != null) {
			product.changeProductTags(tags);
		}
		
		if(sizes != null) {
			product.changeProductSize(sizes);
		}
		
		if(price != null) {
			product.changePrice(price);
		}
		
		if(category != null) {
			product.changeCategory(category);
		}
		productEventHandler.save(product);
		return product;
	}

	@Override
	public Product deleteProduct(
			ProductId targetProductId, 
			Owner owner
		) {
		Product product = productEventHandler.find(targetProductId)
				.orElseThrow(()->new ProductNotFoundException("해당 의류가 존재하지 않습니다."));
		if(!owner.isMyProduct(product)) {
			throw new InvalidProductException("자신이 등록한 의류만 삭제할 수 있습니다.");
		}
		product.delete();
		productEventHandler.save(product);
		return product;
	}

}
