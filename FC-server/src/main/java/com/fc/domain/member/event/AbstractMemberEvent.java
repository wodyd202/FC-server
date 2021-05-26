package com.fc.domain.member.event;

import java.io.Serializable;

import com.fc.core.event.Event;
import com.fc.domain.member.Email;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AbstractMemberEvent implements Event<Email>, Serializable{
	private static final long serialVersionUID = 1L;
	protected Email email;
	
	@Override
	public Email getIdentifier() {
		return this.email;
	}

}
