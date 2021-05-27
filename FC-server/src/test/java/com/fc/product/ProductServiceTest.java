package com.fc.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.fc.command.product.infra.ProductEventHandler;
import com.fc.command.product.model.ProductCommand.CreateProduct;
import com.fc.core.infra.Validator;
import com.fc.domain.product.Owner;
import com.fc.domain.product.Product;
import com.fc.domain.product.SizeList.Size;
import com.fc.domain.store.read.Store;
import com.fc.query.store.infra.StoreRepository;

@SuppressWarnings("unchecked")
public class ProductServiceTest {
	
	StoreRepository storeRepository = mock(StoreRepository.class);
	ProductEventHandler eventHandler = mock(ProductEventHandler.class);
	
	ProductService productService = new SimpleProductService(storeRepository, eventHandler);
	
	@Test
	void 상품_등록() {
		CreateProduct command = CreateProduct
				.builder()
				.title("제목")
				.tags(Arrays.asList("태그1","태그2","태그3"))
				.category("카테고리")
				.price(3000)
				.sizes(Arrays.asList(Size.L))
				.images(Arrays.asList(new MockMultipartFile("image1.jpg", new byte[] {})))
				.build();
		
		Product product = productService.create(mock(Validator.class), new Owner("test@naver.com"), command);
		
		assertThat(product).isNotNull();
	}
	
	@BeforeEach
	void setUp() {
		when(storeRepository.findByOwner(any()))
			.thenReturn(Optional.of(mock(Store.class)));
	}
}
