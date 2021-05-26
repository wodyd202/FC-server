package com.fc.domain.member;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fc.command.member.model.MemberCommand.CreateMember;
import com.fc.core.domain.AggregateRoot;
import com.fc.domain.member.event.ChangedMemberAddress;
import com.fc.domain.member.event.ChangedMemberPassword;
import com.fc.domain.member.event.CovertedToSeller;
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
		super(email);
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
	
	public static Member create(CreateMember command) {
		Member member = new Member(command.getEmail(), command.getPassword());
		return member;
	}
	
	public void changeAddress(Address address) {
		this.address = address;
		applyChange(new ChangedMemberAddress(this.email, this.address));
	}
	
	public void changePassword(Password password) {
		this.password = password;
		applyChange(new ChangedMemberPassword(this.email, this.password));
	}
	
	public void convertToSeller() {
		this.rule = MemberRule.SELLER;
		applyChange(new CovertedToSeller(this.email));
	}
	
	protected void apply(RegisteredMember event) {
		this.email = event.getEmail();
		this.password = event.getPassword();
		this.state = MemberState.CREATE;
		this.rule = MemberRule.BUYER;
		this.createDateTime = new Date();
	}

	protected void apply(ChangedMemberAddress event) {
		this.address = event.getAddress();
	}
	
	protected void apply(ChangedMemberPassword event) {
		this.password = event.getPassword();
	}

}
