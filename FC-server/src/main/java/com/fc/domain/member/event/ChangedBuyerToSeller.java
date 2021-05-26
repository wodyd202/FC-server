package com.fc.domain.member.event;

import com.fc.domain.member.Email;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangedBuyerToSeller extends AbstractMemberEvent{
	private static final long serialVersionUID = 1L;
	public ChangedBuyerToSeller(Email email) {
		this.email = email;
	}
}
