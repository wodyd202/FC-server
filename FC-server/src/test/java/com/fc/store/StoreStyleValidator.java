package com.fc.store;

import java.util.List;

import com.fc.command.store.infra.validator.AbstractStoreValidator;
import com.fc.command.store.model.StoreCommand.ChangeStoreStyle;

/**
  * @Date : 2021. 5. 27. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 스타일 변경시 사용되는 validator
  */
public class StoreStyleValidator extends AbstractStoreValidator<ChangeStoreStyle> {

	@Override
	public void validation(ChangeStoreStyle target) {
		List<String> styles = target.getStyles();
		storeStylesValidation(styles);
	}

}
