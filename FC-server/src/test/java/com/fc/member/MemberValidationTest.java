package com.fc.member;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.fc.core.infra.Validator;
import com.fc.service.common.address.exception.InvalidAddressException;
import com.fc.service.common.address.model.AddressCommand;
import com.fc.service.member.exception.InvalidMemberException;
import com.fc.service.member.infra.CreateMemberValidator;
import com.fc.service.member.infra.PasswordMeter;
import com.fc.service.member.model.MemberCommand;

@SuppressWarnings("unchecked")
public class MemberValidationTest {
	private Validator<AddressCommand> addressValidator = mock(Validator.class);
	Validator<MemberCommand.CreateMemberCommand> validator = new CreateMemberValidator(addressValidator, new PasswordMeter());
	
	@Test
	void 주소가_존재하나_유효한_주소가_아닌경우_실패() {
		AddressCommand address = new AddressCommand(321, 12);
		MemberCommand.CreateMemberCommand command = new MemberCommand.CreateMemberCommand("test@naver.com","password.[]123", address);
		doThrow(InvalidAddressException.class)
				.when(addressValidator)
				.validation(address);
		
		assertThrows(InvalidAddressException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 주소가_없다면_주소_validation을_수행_안함() {
		MemberCommand.CreateMemberCommand command = new MemberCommand.CreateMemberCommand("test@naver.com","password.[]123");
		validator.validation(command);
		
		verify(addressValidator, never())
			.validation(any(AddressCommand.class));
	}
	
	@Test
	void 비밀번호_누락_실패() {
		MemberCommand.CreateMemberCommand command = new MemberCommand.CreateMemberCommand("test@naver.com",null);
		assertThrows(InvalidMemberException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 이메일_누락_실패() {
		MemberCommand.CreateMemberCommand command = new MemberCommand.CreateMemberCommand(null,"password.[]123");
		assertThrows(InvalidMemberException.class, ()->{
			validator.validation(command);
		});
	}
}
