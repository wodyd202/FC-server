package com.fc.command.store.infra.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.store.infra.StoreTagRepository;
import com.fc.command.store.model.StoreCommand.CreateStore;
import com.fc.core.infra.Validator;

import lombok.NoArgsConstructor;

/**
  * @Date : 2021. 5. 27. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 등록 시 사용되는 validator
  */
@Component
@NoArgsConstructor
public class CreateStoreValidator extends AbstractStoreValidator<CreateStore> {
	
	@Override
	public void validation(CreateStore target) {
		String businessName = target.getBusinessName();
		String businessNumber = target.getBusinessNumber();
		String phone = target.getPhone();
		AddressCommand address = target.getAddress();
		String addressDetail = target.getAddressDetail();
		List<String> storeTags = target.getStoreTags();
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
		
		openingHourValidation(weekdayStartTime, weekdayEndTime, weekendStartTime, weekendEndTime, holidays);
	}

	private void openingHourValidation(int weekdayStartTime, int weekdayEndTime, int weekendStartTime, int weekendEndTime,
			List<String> holidays) {
		weekdayOpeningHourValidation(weekdayStartTime, weekdayEndTime);
		weekendOpeningHourValidation(weekendStartTime, weekendEndTime);
		holidayValidation(holidays);
	}
	
	@Autowired
	public CreateStoreValidator(
			StoreTagRepository storeTagRepository,
			Validator<AddressCommand> addressValidator
		) {
		super(storeTagRepository, addressValidator);
	}
}
