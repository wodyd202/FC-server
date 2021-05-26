package com.fc.command.member.infra.validator;

import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.member.exception.InvalidMemberException;
import com.fc.command.member.model.MemberCommand.CreateMember;
import com.fc.core.infra.Validator;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
public class CreateMemberValidator implements Validator<CreateMember> {
	final String EMAIL_REGEX = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
	
	private final Validator<AddressCommand> addressValidator;
	private final PasswordMeter passwordMeter;
	
	@Override
	public void validation(CreateMember target) {
		String email = target.getEmail();
		String password = target.getPassword();
		AddressCommand address = target.getAddress();
		
		emailValidation(email);
		passwordValidation(password);
		
		if(address != null) {
			addressValidator.validation(address);
		}
	}

	private void emailValidation(String email) {
		assertNotEmptyString(email, new InvalidMemberException("사용자 이메일을 입력해주세요."));
		assertRegex(EMAIL_REGEX, email, new InvalidMemberException("이메일 형식으로 입력해주세요."));
	}

	private void passwordValidation(String password) {
		assertNotEmptyString(password, new InvalidMemberException("사용자 비밀번호를 입력해주세요."));
		passwordMeter.meter(password);
	}
}
