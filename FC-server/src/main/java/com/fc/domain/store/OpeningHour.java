package com.fc.domain.store;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Convert;

import com.fc.domain.store.read.HolidayConverter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpeningHour {
	private int weekdayStartTime;
	private int weekdayEndTime;

	private int weekendStartTime;
	private int weekendEndTime;

	@Convert(converter = HolidayConverter.class)
	private Set<Holiday> holidays;

	public OpeningHour(int weekdayStartTime, int weekdayEndTime, int weekendStartTime, int weekendEndTime,
			List<String> holidays) {
		this.weekdayStartTime = weekdayStartTime;
		this.weekdayEndTime = weekdayEndTime;
		this.weekendStartTime = weekendStartTime;
		this.weekendEndTime = weekendEndTime;
		if (holidays != null) {
			this.holidays = holidays.stream().map(Holiday::new).collect(Collectors.toSet());
		}
	}

	public void changeWeekdayOpeningHour(int startTime, int endTime) {
		this.weekdayStartTime = startTime;
		this.weekdayEndTime = endTime;
	}

	public void changeWeekendOpeningHour(int startTime, int endTime) {
		this.weekendStartTime = startTime;
		this.weekendEndTime = endTime;
	}

}
