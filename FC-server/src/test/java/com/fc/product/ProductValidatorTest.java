package com.fc.product;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.fc.command.product.exception.InvalidProductException;
import com.fc.command.product.infra.validator.CreateProductValidator;
import com.fc.command.product.model.ProductCommand.CreateProduct;
import com.fc.core.infra.Validator;
import com.fc.domain.product.SizeList.Size;

public class ProductValidatorTest {
	Validator<CreateProduct> validator = new CreateProductValidator();
	
	@Test
	void 대표이미지_인덱스가_이미지_갯수보다_클때_실패() {
		CreateProduct command = CreateProduct
				.builder()
				.title("제목")
				.tags(Arrays.asList("태그1","태그2","태그3","태그4","태그5"))
				.category("카테고리")
				.price(1000)
				.sizes(Arrays.asList(Size.L))
				.images(Arrays.asList(new MockMultipartFile("image1.jpg", new byte[] {})))
				.mainImageIdx(2)
				.build();
		assertThrows(InvalidProductException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 이미지가_10개_이상일때_실패() {
		CreateProduct command = CreateProduct
				.builder()
				.title("제목")
				.tags(Arrays.asList("태그1","태그2","태그3"))
				.category("카테고리")
				.price(1000)
				.sizes(Arrays.asList(Size.L))
				.images(Arrays.asList(
						new MockMultipartFile("image1.jpg", new byte[] {}),
						new MockMultipartFile("image1.jpg", new byte[] {}),
						new MockMultipartFile("image1.jpg", new byte[] {}),
						new MockMultipartFile("image1.jpg", new byte[] {}),
						new MockMultipartFile("image1.jpg", new byte[] {}),
						new MockMultipartFile("image1.jpg", new byte[] {}),
						new MockMultipartFile("image1.jpg", new byte[] {}),
						new MockMultipartFile("image1.jpg", new byte[] {}),
						new MockMultipartFile("image1.jpg", new byte[] {}),
						new MockMultipartFile("image1.jpg", new byte[] {}),
						new MockMultipartFile("image1.jpg", new byte[] {})
					))
				.build();
		assertThrows(InvalidProductException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 이미지_누락_실패() {
		CreateProduct command = CreateProduct
				.builder()
				.title("제목")
				.tags(Arrays.asList("태그1","태그2","태그3","태그4","태그5"))
				.category("카테고리")
				.price(1000)
				.sizes(Arrays.asList(Size.L))
//				.images(Arrays.asList(new MockMultipartFile("image1.jpg", new byte[] {})))
				.build();
		assertThrows(InvalidProductException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 태그가_3개_이상일때_실패() {
		CreateProduct command = CreateProduct
				.builder()
				.title("제목")
				.tags(Arrays.asList("태그1","태그2","태그3","태그4","태그5"))
				.category("카테고리")
				.price(1000)
				.sizes(Arrays.asList(Size.L))
				.images(Arrays.asList(new MockMultipartFile("image1.jpg", new byte[] {})))
				.build();
		assertThrows(InvalidProductException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 사이즈_중복_입력_실패() {
		CreateProduct command = CreateProduct
				.builder()
				.title("제목")
				.tags(Arrays.asList("태그1","태그2","태그3"))
				.category("카테고리")
				.price(1000)
				.sizes(Arrays.asList(Size.L,Size.L))
				.images(Arrays.asList(new MockMultipartFile("image1.jpg", new byte[] {})))
				.build();
		assertThrows(InvalidProductException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 사이즈_누락_실패() {
		CreateProduct command = CreateProduct
				.builder()
				.title("제목")
				.tags(Arrays.asList("태그1","태그2","태그3"))
				.category("카테고리")
				.price(1000)
//				.sizes(Arrays.asList(Size.L))
				.images(Arrays.asList(new MockMultipartFile("image1.jpg", new byte[] {})))
				.build();
		assertThrows(InvalidProductException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 가격을_0_이하로_입력함_실패() {
		CreateProduct command = CreateProduct
				.builder()
				.title("제목")
				.tags(Arrays.asList("태그1","태그2","태그3"))
				.category("카테고리")
				.price(0)
				.sizes(Arrays.asList(Size.L))
				.images(Arrays.asList(new MockMultipartFile("image1.jpg", new byte[] {})))
				.build();
		assertThrows(InvalidProductException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 분류_누락_실패() {
		CreateProduct command = CreateProduct
				.builder()
				.title("제목")
				.tags(Arrays.asList("태그1","태그2","태그3"))
//				.category("카테고리")
				.price(3000)
				.sizes(Arrays.asList(Size.L))
				.images(Arrays.asList(new MockMultipartFile("image1.jpg", new byte[] {})))
				.build();
		assertThrows(InvalidProductException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 태그_누락_실패() {
		CreateProduct command = CreateProduct
				.builder()
				.title("제목")
//				.tags(Arrays.asList("태그1","태그2","태그3"))
				.category("카테고리")
				.price(3000)
				.sizes(Arrays.asList(Size.L))
				.images(Arrays.asList(new MockMultipartFile("image1.jpg", new byte[] {})))
				.build();
		assertThrows(InvalidProductException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 제목_누락_실패() {
		CreateProduct command = CreateProduct
				.builder()
//				.title("제목")
				.tags(Arrays.asList("태그1","태그2","태그3"))
				.category("카테고리")
				.price(3000)
				.sizes(Arrays.asList(Size.L))
				.images(Arrays.asList(new MockMultipartFile("image1.jpg", new byte[] {})))
				.build();
		assertThrows(InvalidProductException.class, ()->{
			validator.validation(command);
		});
	}
	
}
