package com.fc.domain.member;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fc.command.member.model.MemberCommand.CreateMemberCommand;
import com.fc.core.domain.AggregateRoot;
import com.fc.domain.member.event.RegisteredMember;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AggregateRoot<Email> {
	private static final long serialVersionUID = 1L;
	public enum MemberState { CREATE, DELETE }
	public enum MemberRule { BUYER, SELLER }
	
	Email email;
	Password password;
	Address address;
	MemberState state;
	MemberRule rule;
	Date createDateTime;
	
	@JsonIgnore
	public boolean isDelete() {
		return this.state == MemberState.DELETE;
	}
	
	Member(Email email){
		this.email = email;
	}
	
	Member(String email,String password){
		super(new Email(email));
		this.email = getIdentifier();
		this.password = new Password(password);
		this.state = MemberState.CREATE;
		this.rule = MemberRule.BUYER;
		this.createDateTime = new Date();
		applyChange(new RegisteredMember(this.email,this.password,this.rule));
	}
	
	public static Member create(CreateMemberCommand command) {
		Member member = new Member(command.getEmail(), command.getPassword());
		return member;
	}
	
	public void apply(RegisteredMember event) {
		this.email = event.getEmail();
		this.password = event.getPassword();
		this.state = MemberState.CREATE;
		this.rule = MemberRule.BUYER;
		this.createDateTime = new Date();
	}

}
