package com.fc.domain.member.event;

import com.fc.domain.member.Email;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
  * @Date : 2021. 5. 27. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체등록 시 발생되는 사용자 이벤트
  */
@Getter
@NoArgsConstructor
public class ConvertedToSeller extends AbstractMemberEvent{
	private static final long serialVersionUID = 1L;
	public ConvertedToSeller(Email targetUserEmail) {
		this.email = targetUserEmail;
	}
}
