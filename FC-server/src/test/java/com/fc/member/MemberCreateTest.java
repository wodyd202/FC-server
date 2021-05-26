package com.fc.member;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.command.member.SimpleMemberService;
import com.fc.command.member.exception.AlreadyDeletedMemberException;
import com.fc.command.member.exception.AlreadyExistMemberException;
import com.fc.command.member.infra.SimpleMemberEventStore;
import com.fc.command.member.infra.InmemoryMemberEventStoreRepository;
import com.fc.command.member.infra.InmemoryMemberSnapshotRepository;
import com.fc.command.member.infra.MemberEventHandler;
import com.fc.command.member.infra.MemberEventPublisher;
import com.fc.command.member.infra.MemberEventStoreRepository;
import com.fc.command.member.model.MemberCommand;
import com.fc.core.event.EventProjector;
import com.fc.core.event.EventPublisher;
import com.fc.core.event.EventStore;
import com.fc.core.infra.Validator;
import com.fc.core.snapshot.SnapshotRepository;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;
import com.fc.domain.member.Member.MemberState;
import com.fc.domain.member.event.MemberRawEvent;
import com.fc.query.member.infra.MemberEventProjector;
import com.fc.query.member.infra.MemberJpaRepository;

@SuppressWarnings("unchecked")
public class MemberCreateTest {
	
	SimpleMemberService memberService;
	
	@Test
	void 사용자_등록() {
		MemberCommand.CreateMember command = new MemberCommand.CreateMember("test@naver.com","password.[]123");
		memberService.create(mock(Validator.class), command);
	}
	
	@Test
	@Disabled
	void 중복된_사용자_실패() {
		MemberCommand.CreateMember command = new MemberCommand.CreateMember("test@naver.com","password.[]123");
		memberService.create(mock(Validator.class), command);
		
		assertThrows(AlreadyExistMemberException.class, ()->{
			memberService.create(mock(Validator.class), command);
		});
	}
	
	@Test
	@Disabled
	void 이미탈퇴한_회원이_다시_등록하는_경우_실패() {
		MemberEventHandler memberEventHandler = mock(MemberEventHandler.class);
		memberService.setMemberEventHandler(memberEventHandler);
		
		when(memberEventHandler.find(any(Email.class)))
			.thenReturn(Optional.of(Member.builder()
					.state(MemberState.DELETE)
					.build()));

		MemberCommand.CreateMember command = new MemberCommand.CreateMember("test@naver.com","password.[]123");
		assertThrows(AlreadyDeletedMemberException.class, ()->{
			memberService.create(mock(Validator.class), command);
		});
	}
	
	@BeforeEach
	void setUp() {
		EventPublisher<MemberRawEvent> publisher = new MemberEventPublisher();
		EventProjector projector = new MemberEventProjector(mock(MemberJpaRepository.class));
		
		// 이벤트
		MemberEventStoreRepository eventStoreRepository = new InmemoryMemberEventStoreRepository();
		EventStore<Email> eventStore = new SimpleMemberEventStore(new ObjectMapper(), eventStoreRepository, publisher, projector);

		// 스냅샷
		SnapshotRepository<Member, Email> snapshotRepository = new InmemoryMemberSnapshotRepository();
		
		MemberEventHandler memberEventHandler = new MemberEventHandler(eventStore, snapshotRepository);
		memberService = new SimpleMemberService(memberEventHandler);
	}
}
