package com.fc.member;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fc.command.member.MemberService;
import com.fc.command.member.model.MemberCommand;
import com.fc.core.infra.Validator;

@SpringBootTest
@SuppressWarnings("unchecked")
public class MemberEventProjectorTest {

	@Autowired
	MemberService memberService;
	
	@Test
	void test() {
		MemberCommand.CreateMemberCommand command = new MemberCommand.CreateMemberCommand("test@naver.com","password.[]123");
		memberService.create(mock(Validator.class), command);
	}
}
