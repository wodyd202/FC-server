package com.fc.domain.member;

import java.util.Date;

import com.fc.core.domain.AggregateRoot;

import lombok.Getter;

@Getter
public class Member extends AggregateRoot<Email>{
	private static final long serialVersionUID = 1L;
	public enum MemberState { CREATE, DELETE }
	public enum MemberRule { BUYER, SELLER }
	
	Email email;
	Password password;
	Address address;
	MemberState state;
	MemberRule rule;
	Date createDateTime;
}
