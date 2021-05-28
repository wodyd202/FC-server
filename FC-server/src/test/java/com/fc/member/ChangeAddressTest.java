package com.fc.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fc.command.common.address.exception.InvalidAddressException;
import com.fc.command.common.address.infra.AddressDetailGetter;
import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.member.MemberService;
import com.fc.command.member.SimpleMemberService;
import com.fc.command.member.infra.MemberEventHandler;
import com.fc.command.member.infra.validator.ChangeAddressValidator;
import com.fc.command.member.model.MemberCommand;
import com.fc.core.infra.Validator;
import com.fc.domain.member.Address;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;

public class ChangeAddressTest {

	AddressDetailGetter addressDetailGetter = mock(AddressDetailGetter.class);
	Validator<AddressCommand> validator = new ChangeAddressValidator();
	MemberEventHandler memberEventHandler = mock(MemberEventHandler.class);

	MemberService service = new SimpleMemberService(memberEventHandler,mock(PasswordEncoder.class));

	Member mockMember = Member.builder().build();

	@Test
	void 잘못된_좌표값_입력_실패() {
		MemberCommand.ChangeAddress command = new MemberCommand.ChangeAddress(0,0);
		
		assertThrows(InvalidAddressException.class, ()->{
			service.changeAddress(validator,addressDetailGetter,new Email("test@naver.com"),command);
		});
	}
	
	@Test
	void 정상적인_좌표값_입력() {
		MemberCommand.ChangeAddress command = new MemberCommand.ChangeAddress(3,3);
		
		when(addressDetailGetter.getDetail(any(AddressCommand.class)))
			.thenReturn(new Address());
		
		service.changeAddress(validator,addressDetailGetter,new Email("test@naver.com"),command);
		
		assertThat(mockMember.getAddress()).isNotNull();
	}
	
	@BeforeEach
	void setUp(){
		when(memberEventHandler.find(any()))
			.thenReturn(Optional.of(mockMember));
	}
}
