package com.fc.query.member.infra;

import java.util.Date;

import javax.transaction.Transactional;

import com.fc.command.member.exception.MemberNotFoundException;
import com.fc.core.event.AbstractEventProjector;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member.MemberState;
import com.fc.domain.member.event.ChangedMemberAddress;
import com.fc.domain.member.event.RegisteredMember;
import com.fc.domain.member.read.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberEventProjector extends AbstractEventProjector {
	private final MemberJpaRepository memberJpaRepository;
	
	public void execute(RegisteredMember event) {
		Member member = Member.builder()
				.email(event.getEmail())
				.password(event.getPassword())
				.rule(event.getRule())
				.state(MemberState.CREATE)
				.createDateTime(new Date())
				.build();
		memberJpaRepository.save(member);
		log.info("save query member : {}", event);
	}
	
	public void execute(ChangedMemberAddress event) {
		Email to = event.getIdentifier();
		Member member = memberJpaRepository.findById(to).orElseThrow(()->new MemberNotFoundException("해당 사용자가 존재하지 않습니다."));
		member.changeAddress(event.getAddress());
		memberJpaRepository.save(member);
		log.info("change address member : {}", event);
	}
}
