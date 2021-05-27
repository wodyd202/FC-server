package com.fc.domain.store.event;

import com.fc.domain.store.Owner;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Date : 2021. 5. 26. 
 * @작성자 : LJY
 * @프로그램 설명 : 업체 이름 변경시 발생하는 이벤트
*/
@Getter
@NoArgsConstructor
public class ChangedBusinessName extends AbstractStoreEvent{
	private static final long serialVersionUID = 1L;
	private String businessName;

	public ChangedBusinessName(Owner targetOwner, String businessName) {
		this.owner = targetOwner;
		this.businessName = businessName;
	}
}
