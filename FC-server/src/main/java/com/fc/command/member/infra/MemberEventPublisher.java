package com.fc.command.member.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import com.fc.core.event.EventPublisher;
import com.fc.domain.member.event.MemberRawEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberEventPublisher implements EventPublisher<MemberRawEvent> {

	@Autowired
	private ApplicationEventPublisher publsiher;
	
	@Override
	public void publish(MemberRawEvent rawEvent) {
		if(publsiher != null) {
			publsiher.publishEvent(rawEvent);
			log.info("publish event : {} ", rawEvent);
		}
	}

}
