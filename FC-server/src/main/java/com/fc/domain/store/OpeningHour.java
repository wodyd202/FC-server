package com.fc.domain.store;

import java.util.Set;

import javax.persistence.Convert;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpeningHour {
	private byte weekdayStartTime;
	private byte weekdayEndTime;
	
	private byte weekendStartTime;
	private byte weekendEndTime;
	
	@Convert(converter = HolidayConverter.class)
	private Set<Holiday> holidays;
}
