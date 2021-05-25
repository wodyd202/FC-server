package com.fc.command.member.infra;

import com.fc.core.event.EventPublisher;
import com.fc.domain.member.event.MemberRawEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberEventPublisher implements EventPublisher<MemberRawEvent> {

	@Override
	public void publish(MemberRawEvent rawEvent) {
		log.info("publish event : {} ", rawEvent);
	}

}
