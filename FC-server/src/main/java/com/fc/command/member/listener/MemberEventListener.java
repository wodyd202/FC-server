package com.fc.command.member.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.fc.command.member.infra.MemberEventHandler;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;
import com.fc.domain.store.event.RegisterdStore;

import lombok.RequiredArgsConstructor;

/**
  * @Date : 2021. 5. 27. 
  * @작성자 : LJY
  * @프로그램 설명 : Member Event Listener
  */
@Component
@RequiredArgsConstructor
public class MemberEventListener {
	private final MemberEventHandler eventHandler;
	
	@EventListener
	protected void on(RegisterdStore event) throws ClassNotFoundException {
		Email targetUserEmail = new Email(event.getIdentifier().getEmail());
		Member findMember = eventHandler.find(targetUserEmail).get();
		findMember.convertToSeller();
		eventHandler.save(findMember);
	}
}
