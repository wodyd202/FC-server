package com.fc.query.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fc.core.event.EventProjector;
import com.fc.query.member.infra.MemberEventProjector;
import com.fc.query.member.infra.MemberJpaRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class QueryMemberConfig {
	private final MemberJpaRepository memberJpaRepository;
	
	@Bean
	EventProjector memberEventProjector() {
		return new MemberEventProjector(memberJpaRepository);
	}
}
