package com.fc.domain.member.event;

import com.fc.domain.member.Email;
import com.fc.domain.member.Member.MemberRule;
import com.fc.domain.member.Password;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisteredMember extends AbstractMemberEvent {
	private static final long serialVersionUID = 1L;
	private Password password;
	private MemberRule rule;
	
	public RegisteredMember(Email email, Password password, MemberRule rule) {
		this.email = email;
		this.password = password;
		this.rule = rule;
	}
}
