package com.fc.domain.store.event;

import com.fc.domain.store.Owner;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
  * @Date : 2021. 5. 26. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 판매 중단에서 시작으로 전환시 발생되는 이벤트
  */
@Getter
@NoArgsConstructor
public class StartedSell extends AbstractStoreEvent{
	private static final long serialVersionUID = 1L;
	public StartedSell(Owner targetOwner) {
		this.owner = targetOwner;
	}
}
