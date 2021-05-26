package com.fc.domain.store.event;

import com.fc.domain.store.MainImage;
import com.fc.domain.store.Owner;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
  * @Date : 2021. 5. 26. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 이미지 변경시 발생되는 이벤트
  */
@Getter
@NoArgsConstructor
public class ChangedStoreMainImage extends AbstractStoreEvent{
	private static final long serialVersionUID = 1L;
	private MainImage image;
	
	public ChangedStoreMainImage(Owner targetOwner, MainImage image) {
		this.owner = targetOwner;
		this.image = image;
	}
}
