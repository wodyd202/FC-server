package com.fc.member;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fc.command.member.MemberService;
import com.fc.command.member.SimpleMemberService;
import com.fc.command.member.infra.MemberEventHandler;
import com.fc.command.member.infra.validator.ChangePasswordValidator;
import com.fc.command.member.infra.validator.PasswordMeter;
import com.fc.command.member.model.MemberCommand;
import com.fc.core.infra.Validator;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;
import com.fc.domain.member.Password;
import com.fc.query.product.infra.ProductRepository;
import com.fc.query.store.infra.StoreRepository;

public class ChangePasswordTest {
	MemberEventHandler memberEventHandler = mock(MemberEventHandler.class);
	MemberService service = new SimpleMemberService(memberEventHandler,mock(PasswordEncoder.class), mock(StoreRepository.class), mock(ProductRepository.class));
	Member mockMember = Member.builder().password(new Password("password")).build();

	@Test
	void 사용자_비밀번호_수정() {
		MemberCommand.ChangePassword command = new MemberCommand.ChangePassword("password","password.123[]");
		Validator<MemberCommand.ChangePassword> validator = new ChangePasswordValidator(new PasswordMeter());
		
		when(memberEventHandler.find(any()))
			.thenReturn(Optional.of(mockMember));
		
		service.changePassword(validator, new Email("test@naver.com"), command);
	}
}
