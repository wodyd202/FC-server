package com.fc.query.product.infra;

import org.springframework.transaction.annotation.Transactional;

import com.fc.core.event.AbstractEventProjector;
import com.fc.domain.product.Product.ProductState;
import com.fc.domain.product.event.AddedProductImage;
import com.fc.domain.product.event.ChangedProductCategory;
import com.fc.domain.product.event.ChangedProductPrice;
import com.fc.domain.product.event.ChangedProductSize;
import com.fc.domain.product.event.ChangedProductTags;
import com.fc.domain.product.event.ChangedProductTitle;
import com.fc.domain.product.event.CreatedProduct;
import com.fc.domain.product.event.RemovedProduct;
import com.fc.domain.product.read.Product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProductEventProjector extends AbstractEventProjector {
	private final ProductJpaRepository productJpaRepository;

	protected void execute(CreatedProduct event) {
		Product product = Product
				.builder()
				.id(event.getProductId())
				.title(event.getTitle())
				.tags(event.getProductTags().getTags())
				.category(event.getCategory())
				.price(event.getPrice())
				.sizes(event.getSizes().getSizes())
				.images(event.getImages().getImages())
				.state(ProductState.SELL)
				.createDateTime(event.getCreateDateTime())
				.owner(event.getOwner())
				.build();
		productJpaRepository.save(product);
		log.info("save query product : {}", event);
	}
	
	protected void execute(AddedProductImage event) {
		Product product = productJpaRepository.findById(event.getProductId()).get();
		product.addedProductImage(event.getImage());
		log.info("add product image : {}", event);
	}
	
	protected void execute(ChangedProductCategory event) {
		Product product = productJpaRepository.findById(event.getProductId()).get();
		product.changeProductCategory(event.getCategory());
		log.info("change product image : {}", event);
	}
	
	protected void execute(ChangedProductPrice event) {
		Product product = productJpaRepository.findById(event.getProductId()).get();
		product.changePrice(event.getPrice());
		log.info("change product price : {}", event);
	}
	
	protected void execute(ChangedProductSize event) {
		Product product = productJpaRepository.findById(event.getProductId()).get();
		product.changeSize(event.getSize());
		log.info("change product size : {}", event);
	}
	
	protected void execute(ChangedProductTags event) {
		Product product = productJpaRepository.findById(event.getProductId()).get();
		product.changeTags(event.getTags());
		log.info("change product tags : {}", event);
	}
	
	protected void execute(ChangedProductTitle event) {
		Product product = productJpaRepository.findById(event.getProductId()).get();
		product.changeTitle(event.getTitle());
		log.info("change product title : {}", event);
	}

	protected void execute(RemovedProduct event) {
		Product product = productJpaRepository.findById(event.getProductId()).get();
		product.remove();
		log.info("remove product : {}", event);
	}
}
