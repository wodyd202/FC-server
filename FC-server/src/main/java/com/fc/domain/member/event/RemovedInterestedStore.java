package com.fc.domain.member.event;

import com.fc.domain.member.Email;
import com.fc.domain.member.StoreOwner;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RemovedInterestedStore extends AbstractMemberEvent {
	private static final long serialVersionUID = 1L;
	private StoreOwner owner;

	public RemovedInterestedStore(Email email, StoreOwner owner) {
		this.email = email;
		this.owner = owner;
	}
}
