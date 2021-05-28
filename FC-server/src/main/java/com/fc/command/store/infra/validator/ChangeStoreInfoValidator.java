package com.fc.command.store.infra.validator;

import org.springframework.stereotype.Component;

import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.store.exception.InvalidStoreException;
import com.fc.command.store.model.StoreCommand.ChangeStoreInfo;

/**
  * @Date : 2021. 5. 27. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 기본 정보 변경시 사용되는 validator
  */
@Component
public class ChangeStoreInfoValidator extends AbstractStoreValidator<ChangeStoreInfo> {

	@Override
	public void validation(ChangeStoreInfo target) {
		AddressCommand address = target.getAddress();
		String addressDetail = target.getAddressDetail();
		String businessName = target.getBusinessName();
		String businessNumber = target.getBusinessNumber();
		String phone = target.getPhone();

		if(address == null && addressDetail == null && businessName == null && businessNumber == null && phone == null) {
			throw new InvalidStoreException("변경할 업체 정보를 적어도 하나 이상 입력해주세요.");
		}
		if(address != null && addressDetail != null) {
			addressValidation(address, addressDetail);
		}
		if(businessName != null) {
			businessNameValidation(businessName);
		}
		if(businessNumber != null) {
			businessNumberValidation(businessNumber);
		}
		if(phone != null) {
			phoneValidation(phone);
		}
	}

}
