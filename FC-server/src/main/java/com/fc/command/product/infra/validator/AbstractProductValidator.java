package com.fc.command.product.infra.validator;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fc.command.product.exception.InvalidProductException;
import com.fc.core.infra.Validator;
import com.fc.domain.product.SizeList.Size;

abstract public class AbstractProductValidator<T> implements Validator<T>{
	private final String TITLE_REGEX = "^[a-zA-Z가-힣ㄱ-ㅎ_ ]*{1,20}$";
	
	protected void sizeValidation(List<Size> sizes) {
		assertNotNullObject(sizes, new InvalidProductException("의류 사이즈를 최소 하나 이상 등록해주세요."));
		assertNotOverMaxSizeCollectionSize(Size.values().length, sizes, new InvalidProductException("의류 사이즈를 다시 등록해주세요."));
		assertDuplicateValueInCollection(sizes, new InvalidProductException("의류 사이즈를 다시 등록해주세요."));
	}
	
	protected void imageValidation(List<MultipartFile> files) {
		assertNotNullObject(files, new InvalidProductException("의류 이미지를 1개 이상 5개 이하로 등록해주세요."));
		assertNotOverMaxSizeCollectionSize(5, files, new InvalidProductException("의류 이미지를 1개 이상 5개 이하로 등록해주세요."));
		files.forEach(file->{
			assertImageFile(file, new InvalidProductException("이미지 파일만 등록해주세요."));
		});
	}
	
	protected void mainImageIdxValidation(List<MultipartFile> files, int mainImageIdx) {
		if(files == null) {
			throw new InvalidProductException("의류 이미지를 1개 이상 10개 이하로 등록해주세요.");
		}
		if(mainImageIdx < 0 || files.size() < mainImageIdx - 1) {
			throw new InvalidProductException("대표 이미지 번호를 다시 입력해주세요.");
		}
	}
	
	protected void priceValidation(int price) {
		if(price <= 0) {
			throw new InvalidProductException("가격은 10원 이상으로 입력해주세요.");
		}
		if(price % 10 != 0) {
			throw new InvalidProductException("가격을 다시 입력해주세요.");
		}
	}
	
	protected void categoryValidation(String category) {
		assertNotEmptyString(category, new InvalidProductException("의류 분류를 입력해주세요."));
	}
	
	protected void tagsValidation(List<String> tags) {
		assertNotNullObject(tags, new InvalidProductException("의류 태그를 1개 이상 3개 이하로 등록해주세요."));
		assertNotOverMaxSizeCollectionSize(3, tags, new InvalidProductException("의류 태그를 1개 이상 3개 이하로 등록해주세요."));
		assertDuplicateValueInCollection(tags, new InvalidProductException("의류 태그를 다시 입력해주세요."));
	}
	
	protected void titleValidation(String title) {
		assertNotEmptyString(title, new InvalidProductException("의류명을 입력해주세요."));
		assertRegex(TITLE_REGEX, title, new InvalidProductException("의류명을 [한글,영어(대,소문자),숫자] 조합으로 1자 이상 20자 이하로 입력해주세요."));
	}
}
