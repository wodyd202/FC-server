package com.fc.domain.store.event;

import com.fc.domain.member.Address;
import com.fc.domain.store.Owner;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Date : 2021. 5. 26.
 * @작성자 : LJY
 * @프로그램 설명 : 업체 주소 변경시 발생되는 이벤트
 */
@Getter
@NoArgsConstructor
public class ChangedStoreAddress extends AbstractStoreEvent {
	private static final long serialVersionUID = 1L;
	private Address address;
	private String addressDetail;
	
	public ChangedStoreAddress(Owner owner, Address address, String addressDetail) {
		this.owner = owner;
		this.address = address;
		this.addressDetail= addressDetail;
	}
}
