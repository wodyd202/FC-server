package com.fc.member;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.member.exception.InvalidMemberException;
import com.fc.command.member.infra.validator.CreateMemberValidator;
import com.fc.command.member.infra.validator.PasswordMeter;
import com.fc.command.member.model.MemberCommand;
import com.fc.core.infra.Validator;

@SuppressWarnings("unchecked")
public class MemberValidationTest {
	private Validator<AddressCommand> addressValidator = mock(Validator.class);
	Validator<MemberCommand.CreateMember> validator = new CreateMemberValidator(new PasswordMeter());

	@Test
	void 주소가_없다면_주소_validation을_수행_안함() {
		MemberCommand.CreateMember command = new MemberCommand.CreateMember("test@naver.com","password.[]123");
		validator.validation(command);
		
		verify(addressValidator, never())
			.validation(any(AddressCommand.class));
	}
	
	@Test
	void 비밀번호_누락_실패() {
		MemberCommand.CreateMember command = new MemberCommand.CreateMember("test@naver.com",null);
		assertThrows(InvalidMemberException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 이메일_누락_실패() {
		MemberCommand.CreateMember command = new MemberCommand.CreateMember(null,"password.[]123");
		assertThrows(InvalidMemberException.class, ()->{
			validator.validation(command);
		});
	}
}
