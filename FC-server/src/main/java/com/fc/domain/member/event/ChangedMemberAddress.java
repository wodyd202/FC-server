package com.fc.domain.member.event;

import com.fc.domain.member.Address;
import com.fc.domain.member.Email;

import lombok.Getter;

@Getter
public class ChangedMemberAddress extends AbstractMemberEvent{
	private static final long serialVersionUID = 1L;
	private Address address;
	
	public ChangedMemberAddress(Email email, Address address) {
		this.email = email;
		this.address = address;
	}
}
