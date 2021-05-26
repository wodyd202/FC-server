package com.fc.domain.store.event;

import com.fc.domain.store.Owner;
import com.fc.domain.store.Phone;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
  * @Date : 2021. 5. 26. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 전화번호 변경시 발생되는 이벤트
  */
@Getter
@NoArgsConstructor
public class ChangedStorePhone extends AbstractStoreEvent{
	private static final long serialVersionUID = 1L;
	private Phone phone;
	
	public ChangedStorePhone(Owner targetOwner, Phone phone) {
		this.owner = targetOwner;
		this.phone = phone;
	}

}
