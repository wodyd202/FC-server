package com.fc.command.store.infra.validator;

import java.util.List;

import com.fc.command.store.exception.InvalidStoreException;
import com.fc.command.store.infra.validator.AbstractStoreValidator;
import com.fc.command.store.model.StoreCommand.ChangeOpeningHour;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OpeningHourValidator extends AbstractStoreValidator<ChangeOpeningHour> {

	@Override
	public void validation(ChangeOpeningHour target) {
		Integer weekdayStartTime = target.getWeekdayStartTime();
		Integer weekdayEndTime = target.getWeekdayEndTime();
		Integer weekendStartTime = target.getWeekendStartTime();
		Integer weekendEndTime = target.getWeekendEndTime();
		List<String> holidays = target.getHolidays();

		if (isEmptyAll(weekdayStartTime, weekdayEndTime, weekendStartTime, weekendEndTime, holidays)) {
			throw new InvalidStoreException("평일, 주말, 휴일 중 적어도 하나 이상의 운영시간을 입력해주세요.");
		}
		
		if(weekdayStartTime != null || weekdayEndTime != null) {
			assertExistAll(new InvalidStoreException("평일 영업시간을 다시 입력해주세요."), weekdayStartTime, weekdayEndTime);
			weekdayOpeningHourValidation(weekdayStartTime, weekdayEndTime);
		}
		
		if(weekendStartTime != null || weekendEndTime != null) {
			assertExistAll(new InvalidStoreException("주말 영업시간을 다시 입력해주세요."), weekendStartTime, weekendEndTime);
			weekendOpeningHourValidation(weekendStartTime, weekendEndTime);
		}
		
		if(holidays != null) {
			holidayValidation(holidays);
		}
	}
	
	private boolean isEmptyAll(Integer weekdayStartTime, Integer weekdayEndTime, Integer weekendStartTime,
			Integer weekendEndTime, List<String> holidays) {
		return weekdayStartTime == null && weekdayEndTime == null && weekendStartTime == null && weekendEndTime == null
				&& holidays == null;
	}

}
