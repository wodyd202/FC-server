package com.fc.domain.store;

import java.io.Serializable;

import com.fc.domain.member.read.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "email")
public class Owner implements Serializable{
	private static final long serialVersionUID = 1L;
	private String email;
	
	public static Owner withMember(Member member) {
		return new Owner(member.getEmail().getValue());
	}
}
