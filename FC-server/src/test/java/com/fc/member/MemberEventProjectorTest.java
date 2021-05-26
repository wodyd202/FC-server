package com.fc.member;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fc.command.common.address.infra.AddressDetailGetter;
import com.fc.command.member.MemberService;
import com.fc.command.member.infra.validator.ChangeAddressValidator;
import com.fc.command.member.infra.validator.ChangePasswordValidator;
import com.fc.command.member.model.MemberCommand;
import com.fc.core.infra.Validator;
import com.fc.domain.member.Email;

@SpringBootTest
@SuppressWarnings("unchecked")
public class MemberEventProjectorTest {

	@Autowired
	MemberService memberService;
	
	@Autowired
	AddressDetailGetter getter;

	@Test
	void 회원가입_후_주소_변경() {
		MemberCommand.CreateMember createMemberCommand = new MemberCommand.CreateMember("test@naver.com","password.[]123");
		memberService.create(mock(Validator.class), createMemberCommand);
		
		MemberCommand.ChangeAddress changeAddressCommand = new MemberCommand.ChangeAddress(127.1163593869371,37.40612091848614);
		memberService.changeAddress(new ChangeAddressValidator(), getter, new Email("test@naver.com"), changeAddressCommand);

		MemberCommand.ChangePassword command = new MemberCommand.ChangePassword("password.[]123","password.123[]");
		memberService.changePassword(mock(ChangePasswordValidator.class), new Email("test@naver.com"), command);
	}
	
	void 회원가입() {
		MemberCommand.CreateMember command = new MemberCommand.CreateMember("test@naver.com","password.[]123");
		memberService.create(mock(Validator.class), command);
	}
}
