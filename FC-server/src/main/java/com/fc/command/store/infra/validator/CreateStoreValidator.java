package com.fc.command.store.infra.validator;

import java.util.List;

import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.store.model.StoreCommand.CreateStore;
import com.fc.core.infra.Validator;

/**
  * @Date : 2021. 5. 27. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 등록 시 사용되는 validator
  */
public class CreateStoreValidator extends AbstractStoreValidator<CreateStore> {
	
	@Override
	public void validation(CreateStore target) {
		String businessName = target.getBusinessName();
		String businessNumber = target.getBusinessNumber();
		String phone = target.getPhone();
		AddressCommand address = target.getAddress();
		String addressDetail = target.getAddressDetail();
		List<String> storeTags = target.getStoreTags();
		List<String> storeStyles = target.getStoreStyles();
		int weekdayStartTime = target.getWeekdayStartTime();
		int weekdayEndTime = target.getWeekdayEndTime();
		int weekendStartTime = target.getWeekendStartTime();
		int weekendEndTime = target.getWeekendEndTime();
		List<String> holidays = target.getHolidays();
		
		businessNameValidation(businessName);
		businessNumberValidation(businessNumber);
		phoneValidation(phone);
		addressValidation(address, addressDetail);
		storeTagsValidation(storeTags);
		storeStylesValidation(storeStyles);
		openingHourValidation(weekdayStartTime, weekdayEndTime, weekendStartTime, weekendEndTime, holidays);
	}
	
	public CreateStoreValidator(Validator<AddressCommand> addressValidator) {
		super(addressValidator);
	}
}
