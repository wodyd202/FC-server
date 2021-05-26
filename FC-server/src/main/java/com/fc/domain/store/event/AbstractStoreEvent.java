package com.fc.domain.store.event;

import java.io.Serializable;

import com.fc.core.event.Event;
import com.fc.domain.store.Owner;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
  * @Date : 2021. 5. 26. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 이벤트
  */
@Getter
@NoArgsConstructor
public class AbstractStoreEvent implements Event<Owner>, Serializable{
	private static final long serialVersionUID = 1L;
	protected Owner owner;
	
	@Override
	public Owner getIdentifier() {
		return this.owner;
	}

}