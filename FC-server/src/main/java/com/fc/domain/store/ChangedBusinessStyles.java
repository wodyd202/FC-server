package com.fc.domain.store;

import com.fc.domain.store.event.AbstractStoreEvent;

import lombok.Getter;
import lombok.NoArgsConstructor;


/**
  * @Date : 2021. 5. 26. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 스타일 변경시 발생하는 이벤트
*/

@Getter
@NoArgsConstructor
public class ChangedBusinessStyles extends AbstractStoreEvent{
	private static final long serialVersionUID = 1L;
	private StoreStyles styles;
	
	public ChangedBusinessStyles(Owner targetOwner, StoreStyles styles) {
		this.owner = targetOwner;
		this.styles = styles;
	}
}
