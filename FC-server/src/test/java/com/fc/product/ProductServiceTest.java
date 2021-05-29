package com.fc.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.fc.command.product.ProductService;
import com.fc.command.product.SimpleProductService;
import com.fc.command.product.infra.ProductEventHandler;
import com.fc.command.product.model.ProductCommand.ChangeProductInfo;
import com.fc.command.product.model.ProductCommand.CreateProduct;
import com.fc.core.fileUploader.FileUploader;
import com.fc.core.infra.Validator;
import com.fc.domain.product.Owner;
import com.fc.domain.product.Product;
import com.fc.domain.product.ProductId;
import com.fc.domain.product.SizeList.Size;
import com.fc.query.store.infra.StoreRepository;
import com.fc.query.store.model.StoreQuery;

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
		
		Product product = productService.create(mock(Validator.class), mock(FileUploader.class), new Owner("test@naver.com"), command);
		
		assertThat(product).isNotNull();
	}
	
	@Test
	void 상품_타이틀_수정() {
		Owner mockOwner = mock(Owner.class);

		when(mockOwner.isMyProduct(any()))
			.thenReturn(true);
		
		Product mockProduct = mock(Product.class);
		
		when(eventHandler.find(any()))
			.thenReturn(Optional.of(mockProduct));
		
		ChangeProductInfo command = ChangeProductInfo
				.builder()
				.title("제목")
//				.tags(Arrays.asList("태그1","태그2","태그3"))
//				.category("카테고리")
//				.price(3000)
//				.sizes(Arrays.asList(Size.L))
				.build();
		ProductId targetProductId = new ProductId("productId");
		productService.changeProductInfo(mock(Validator.class), targetProductId, mockOwner, command);
		
		verify(mockProduct,times(1))
			.changeProductTitle(any());
		verify(mockProduct,never())
			.changeProductTags(any());
		verify(mockProduct,never())
			.changeCategory(any());
		verify(mockProduct,never())
			.changePrice(any());
		verify(mockProduct,never())
			.changeProductSize(any());
	}
	
	@BeforeEach
	void setUp() {
		when(storeRepository.findByOwner(any()))
			.thenReturn(Optional.of(mock(StoreQuery.StoreMainInfo.class)));
	}
}
