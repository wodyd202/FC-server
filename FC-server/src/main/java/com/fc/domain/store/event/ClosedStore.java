package com.fc.domain.store.event;

import com.fc.domain.store.Owner;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
  * @Date : 2021. 5. 26. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 폐업시 발생되는 이벤트
  */
@Getter
@NoArgsConstructor
public class ClosedStore extends AbstractStoreEvent{
	private static final long serialVersionUID = 1L;
	public ClosedStore(Owner targetOwner) {
		this.owner = targetOwner;
	}
}
