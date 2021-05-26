package com.fc.domain.store.event;

import com.fc.domain.store.Owner;
import com.fc.domain.store.StoreTags;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
  * @Date : 2021. 5. 26. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 태그 변경시 발생되는 이벤트
  */
@Getter
@NoArgsConstructor
public class ChangedStoreTags extends AbstractStoreEvent{
	private static final long serialVersionUID = 1L;
	private StoreTags tags;
	
	public ChangedStoreTags(Owner targetOwner, StoreTags tags) {
		this.owner = targetOwner;
		this.tags = tags;
	}
}
