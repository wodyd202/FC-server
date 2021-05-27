package com.fc.domain.store.event;

import com.fc.domain.store.Owner;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Date : 2021. 5. 26.
 * @작성자 : LJY
 * @프로그램 설명 : 업체 평일 영업시간 변경시 발생되는 이벤트
 */
@Getter
@NoArgsConstructor
public class ChangedWeekdayOpeningHour extends AbstractStoreEvent {
	private static final long serialVersionUID = 1L;
	private int startTime;
	private int endTime;

	public ChangedWeekdayOpeningHour(Owner targetOwner, int startTime, int endTime) {
		this.owner = targetOwner;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
