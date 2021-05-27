package com.fc.command.store.infra.validator;

import java.util.ArrayList;
import java.util.List;

import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.store.exception.InvalidStoreException;
import com.fc.core.infra.Validator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
abstract public class AbstractStoreValidator<T> implements Validator<T> {
	private Validator<AddressCommand> addressValidator;
	private final String BUSINESSNAME_REGEX = "^[가-힣a-zA-Z0-9_ ]*{1,15}$";
	private final String BUSINESS_NUMBER_REGEX = "^[0-9]*{10}$";
	private final String PHONE_REGEX = "^\\d{3}-\\d{3,4}-\\d{4}$";
	private final List<String> HOLIDAY_REGEX = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("월"); add("화"); add("수"); add("목"); add("금"); add("토"); add("일");
	}};
	
	protected void businessNameValidation(String businessName) {
		assertNotEmptyString(businessName, new InvalidStoreException("상호명을 입력해주세요."));
		assertRegex(BUSINESSNAME_REGEX, businessName, new InvalidStoreException("상호명은 [한글,영문(대,소문자),숫자,공백] 조합으로 1자 이상 15자 이하로 입력해주세요."));
	}
	
	protected void businessNumberValidation(String businessNumber) {
		assertNotEmptyString(businessNumber, new InvalidStoreException("사업자 번호를 입력해주세요."));
		assertRegex(BUSINESS_NUMBER_REGEX, businessNumber, new InvalidStoreException("사업자 번호 양식이 올바르지 않습니다. 다시 입력해주세요."));
	}
	
	protected void phoneValidation(String phone) {
		assertNotEmptyString(phone, new InvalidStoreException("전화번호를 입력해주세요."));
		assertRegex(PHONE_REGEX, phone, new InvalidStoreException("전화번호 형식이 잘못되었습니다. 다시 입력해주세요."));
	}

	protected void addressValidation(AddressCommand address, String addressDetail) {
		addressValidator.validation(address);
		assertNotEmptyString(addressDetail, new InvalidStoreException("상세 주소를 입력해주세요."));
	}

	protected void storeTagsValidation(List<String> storeTags) {
		assertNotNullObject(storeTags, new InvalidStoreException("업체 태그를 최대 3개까지 입력해주세요."));
		storeTags.forEach(val->assertNotEmptyString(val, new InvalidStoreException("업체 태그 중 빈값이 존재합니다.")));
		assertNotOverMaxSizeCollectionSize(3, storeTags, new InvalidStoreException("업체 태그를 최대 3개까지 입력해주세요."));
	}
	
	protected void storeStylesValidation(List<String> storeStyles) {
		assertNotNullObject(storeStyles, new InvalidStoreException("업체 스타일을 최대 3개까지 입력해주세요."));
		storeStyles.forEach(val->assertNotEmptyString(val, new InvalidStoreException("업체 스타일 중 빈값이 존재합니다.")));
		assertNotOverMaxSizeCollectionSize(3, storeStyles, new InvalidStoreException("업체 스타일을 최대 3개까지 입력해주세요."));
	}
	
	protected void weekdayOpeningHourValidation(int weekdayStartTime, int weekdayEndTime) {
		if(weekdayStartTime < 0 || weekdayStartTime > 24) {
			throw new InvalidStoreException("평일 영업 시작 시간을 0~24 사이로 입력해주세요.");
		}

		if(weekdayEndTime < 0 || weekdayEndTime > 24) {
			throw new InvalidStoreException("평일 영업 종료 시간을 0~24 사이로 입력해주세요.");
		}
		
		if(weekdayStartTime >= weekdayEndTime) {
			throw new InvalidStoreException("평일 영업 시작시간은 마감시간보다 반드시 작아야합니다.");
		}
	}
	
	protected void weekendOpeningHourValidation(int weekendStartTime,int weekendEndTime) {
		if(weekendStartTime < 0 || weekendStartTime > 24) {
			throw new InvalidStoreException("주말 영업 시작 시간을 0~24 사이로 입력해주세요.");
		}
		
		if(weekendEndTime < 0 || weekendEndTime > 24) {
			throw new InvalidStoreException("주말 영업 종료 시간을 0~24 사이로 입력해주세요.");
		}
		
		if(weekendStartTime >= weekendEndTime) {
			throw new InvalidStoreException("주말 영업 시작시간은 마감시간보다 반드시 작아야합니다.");
		}
	}
	
	protected void holidayValidation(List<String> holidays) {
		if(holidays != null) {
			assertNotOverMaxSizeCollectionSize(7, holidays, new InvalidStoreException("업체 스타일을 최대 3개까지 입력해주세요."));
		}
		
		holidays.forEach(val->{
			if(!HOLIDAY_REGEX.contains(val)) {
				throw new InvalidStoreException("휴일로 지정할 요일이 잘못 입력되었습니다.");
			}
		});
	}
}
