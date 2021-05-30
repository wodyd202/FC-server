package com.fc.domain.member;

import java.io.Serializable;

import javax.persistence.Embeddable;

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
@Embeddable
public class StoreOwner implements Serializable{
	private static final long serialVersionUID = 1L;
	private String email;
	
	public static StoreOwner withMember(Member member) {
		return new StoreOwner(member.getEmail().getValue());
	}
}
