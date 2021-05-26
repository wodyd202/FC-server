package com.fc.domain.member.event;

import com.fc.domain.member.Email;
import com.fc.domain.member.Password;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangedMemberPassword extends AbstractMemberEvent{
	private static final long serialVersionUID = 1L;
	private Password password;
	
	public ChangedMemberPassword(Email email, Password password) {
		this.email = email;
		this.password = password;
	}
}
