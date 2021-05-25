package com.fc.service.member.infra;

import com.fc.core.event.AbstractEventProjector;
import com.fc.domain.member.event.RegisteredMember;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberEventProjector extends AbstractEventProjector {
	public void execute(RegisteredMember event) {
		log.info("save query member : {}", event);
	}
}
