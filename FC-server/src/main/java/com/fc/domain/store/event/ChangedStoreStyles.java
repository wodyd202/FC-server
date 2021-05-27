package com.fc.domain.store.event;

import com.fc.domain.store.Owner;
import com.fc.domain.store.StoreStyles;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
  * @Date : 2021. 5. 27. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 스타일 변경시 발생되는 이벤트
  */
@Getter
@NoArgsConstructor
public class ChangedStoreStyles extends AbstractStoreEvent{
	private static final long serialVersionUID = 1L;
	private StoreStyles styles;
	public ChangedStoreStyles(Owner targetOwner, StoreStyles styles) {
		this.owner = targetOwner;
		this.styles = styles;
	}

}
