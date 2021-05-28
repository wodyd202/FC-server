package com.fc.command.store.infra.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fc.command.store.exception.InvalidStoreException;
import com.fc.command.store.model.StoreCommand.ChangeStoreImage;
import com.fc.core.infra.Validator;


@Component
public class ImageFileValidator implements Validator<ChangeStoreImage> {

	@Override
	public void validation(ChangeStoreImage target) {
		MultipartFile file = target.getFile();
		assertNotNullObject(file, new InvalidStoreException("이미지를 선택해 넣어주세요."));
		assertImageFile(file, new InvalidStoreException("이미지 파일만 등록할 수 있습니다."));
	}

}
