package com.fc.service.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.core.event.EventProjector;
import com.fc.core.event.EventPublisher;
import com.fc.core.event.EventStore;
import com.fc.core.snapshot.SnapshotRepository;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;
import com.fc.domain.member.event.MemberRawEvent;
import com.fc.service.member.infra.InmemoryMemberEventStore;
import com.fc.service.member.infra.InmemoryMemberEventStoreRepository;
import com.fc.service.member.infra.InmemoryMemberSnapshotRepository;
import com.fc.service.member.infra.MemberEventHandler;
import com.fc.service.member.infra.MemberEventProjector;
import com.fc.service.member.infra.MemberEventPublisher;
import com.fc.service.member.infra.MemberEventStoreRepository;

@Configuration
public class MemberConfig {
	
	@Bean
	MemberEventHandler memberEventHandler(ObjectMapper objectMapper) {
		return new MemberEventHandler(memberEventStore(objectMapper), memberSnapshotRepository());
	}
	
	@Bean
	SnapshotRepository<Member, Email> memberSnapshotRepository(){
		return new InmemoryMemberSnapshotRepository();
	}
	
	@Bean
	EventStore<Email> memberEventStore(ObjectMapper objectMapper){
		return new InmemoryMemberEventStore(objectMapper, memberEventStoreRepository(), memberEventPublisher(), memberEventProjector());
	}
	
	@Bean
	MemberEventStoreRepository memberEventStoreRepository() {
		return new InmemoryMemberEventStoreRepository();
	}
	
	@Bean
	EventProjector memberEventProjector() {
		return new MemberEventProjector();
	}
	
	@Bean
	EventPublisher<MemberRawEvent> memberEventPublisher(){
		return new MemberEventPublisher();
	}
}
