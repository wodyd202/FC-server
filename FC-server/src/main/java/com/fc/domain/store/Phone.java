package com.fc.domain.store;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Phone {
	private String first;
	private String second;
	private String third;
	
	public Phone(String phone) {
		int firstIdx = phone.indexOf("-");
		int lastIdx = phone.lastIndexOf("-");
		this.first = phone.substring(0,firstIdx);
		this.second = phone.substring(firstIdx + 1,lastIdx);
		this.third = phone.substring(lastIdx + 1,phone.length());
	}
}
