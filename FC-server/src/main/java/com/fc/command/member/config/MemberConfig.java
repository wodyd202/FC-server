package com.fc.command.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.command.member.infra.JdbcMemberEventRepository;
import com.fc.command.member.infra.JdbcMemberSnapshotRepository;
import com.fc.command.member.infra.MemberEventHandler;
import com.fc.command.member.infra.MemberEventPublisher;
import com.fc.command.member.infra.MemberEventStoreRepository;
import com.fc.command.member.infra.SimpleMemberEventStore;
import com.fc.core.event.EventProjector;
import com.fc.core.event.EventPublisher;
import com.fc.core.event.EventStore;
import com.fc.core.snapshot.SnapshotRepository;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;
import com.fc.domain.member.event.MemberRawEvent;

@Configuration
public class MemberConfig {
	
	@Bean
	MemberEventHandler memberEventHandler(ObjectMapper objectMapper, EventProjector memberEventProjector) {
		return new MemberEventHandler(memberEventStore(objectMapper, memberEventProjector), memberSnapshotRepository());
	}
	
	@Bean
	SnapshotRepository<Member, Email> memberSnapshotRepository(){
		return new JdbcMemberSnapshotRepository();
	}
	
	@Bean
	EventStore<Email> memberEventStore(ObjectMapper objectMapper, EventProjector memberEventProjector){
		return new SimpleMemberEventStore(objectMapper, memberEventStoreRepository(), memberEventPublisher(), memberEventProjector);
	}
	
	@Bean
	MemberEventStoreRepository memberEventStoreRepository() {
		return new JdbcMemberEventRepository();
	}
	
	@Bean
	EventPublisher<MemberRawEvent> memberEventPublisher(){
		return new MemberEventPublisher();
	}
}
