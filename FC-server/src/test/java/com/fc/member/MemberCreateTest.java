package com.fc.member;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.core.event.EventProjector;
import com.fc.core.event.EventPublisher;
import com.fc.core.event.EventStore;
import com.fc.core.infra.Validator;
import com.fc.core.snapshot.SnapshotRepository;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;
import com.fc.domain.member.Member.MemberState;
import com.fc.domain.member.event.MemberRawEvent;
import com.fc.service.member.SimpleMemberService;
import com.fc.service.member.exception.AlreadyExistMemberException;
import com.fc.service.member.exception.AlreadyDeletedMemberException;
import com.fc.service.member.infra.InmemoryMemberEventStore;
import com.fc.service.member.infra.InmemoryMemberEventStoreRepository;
import com.fc.service.member.infra.InmemoryMemberSnapshotRepository;
import com.fc.service.member.infra.MemberEventHandler;
import com.fc.service.member.infra.MemberEventProjector;
import com.fc.service.member.infra.MemberEventPublisher;
import com.fc.service.member.infra.MemberEventStoreRepository;
import com.fc.service.member.model.MemberCommand;

@SuppressWarnings("unchecked")
public class MemberCreateTest {
	
	SimpleMemberService memberService;
	
	@Test
	void 사용자_등록() {
		MemberCommand.CreateMemberCommand command = new MemberCommand.CreateMemberCommand("test@naver.com","password.[]123");
		memberService.create(mock(Validator.class), command);
	}
	
	@Test
	void 중복된_사용자_실패() {
		MemberCommand.CreateMemberCommand command = new MemberCommand.CreateMemberCommand("test@naver.com","password.[]123");
		memberService.create(mock(Validator.class), command);
		
		assertThrows(AlreadyExistMemberException.class, ()->{
			memberService.create(mock(Validator.class), command);
		});
	}
	
	@Test
	void 이미탈퇴한_회원이_다시_등록하는_경우_실패() {
		MemberEventHandler memberEventHandler = mock(MemberEventHandler.class);
		memberService.setMemberEventHandler(memberEventHandler);
		
		when(memberEventHandler.find(any(Email.class)))
			.thenReturn(Optional.of(Member.builder()
					.state(MemberState.DELETE)
					.build()));

		MemberCommand.CreateMemberCommand command = new MemberCommand.CreateMemberCommand("test@naver.com","password.[]123");
		assertThrows(AlreadyDeletedMemberException.class, ()->{
			memberService.create(mock(Validator.class), command);
		});
	}
	
	@BeforeEach
	void setUp() {
		EventPublisher<MemberRawEvent> publisher = new MemberEventPublisher();
		EventProjector projector = new MemberEventProjector();
		
		// 이벤트
		MemberEventStoreRepository eventStoreRepository = new InmemoryMemberEventStoreRepository();
		EventStore<Email> eventStore = new InmemoryMemberEventStore(new ObjectMapper(), eventStoreRepository, publisher, projector);

		// 스냅샷
		SnapshotRepository<Member, Email> snapshotRepository = new InmemoryMemberSnapshotRepository();
		
		MemberEventHandler memberEventHandler = new MemberEventHandler(eventStore, snapshotRepository);
		memberService = new SimpleMemberService(memberEventHandler);
	}
}
