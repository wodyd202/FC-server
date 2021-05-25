package com.fc.service.member.infra;

import java.util.regex.Pattern;

import com.fc.core.infra.Validator;
import com.fc.service.common.address.model.AddressCommand;
import com.fc.service.member.exception.InvalidMemberException;
import com.fc.service.member.infra.PasswordMeter.PasswordStrength;
import com.fc.service.member.model.MemberCommand.CreateMemberCommand;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
public class CreateMemberValidator implements Validator<CreateMemberCommand> {
	private final Validator<AddressCommand> addressValidator;
	private final PasswordMeter passwordMeter;
	
	@Override
	public void validation(CreateMemberCommand target) {
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
		boolean isEmpty = email == null || email.isEmpty();
		if(isEmpty) {
			throw new InvalidMemberException("사용자 이메일을 입력해주세요.");
		}
		isEmail(email);
	}

	private void isEmail(String email) {
		final String regex = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
		if(!Pattern.matches(regex, email)) {
			throw new InvalidMemberException("이메일 형식으로 입력해주세요.");
		}
	}

	private void passwordValidation(String password) {
		boolean isEmpty = password == null || password.isEmpty();
		if(isEmpty) {
			throw new InvalidMemberException("사용자 비밀번호를 입력해주세요.");
		}
		meterPassword(password);
	}

	private void meterPassword(String password) {
		PasswordStrength meter = passwordMeter.meter(password);
		if(meter == PasswordStrength.INVALID) {
			throw new InvalidMemberException("사용자 비밀번호 형식이 올바르지 않습니다.");
		}
		if(meter == PasswordStrength.WEEK) {
			throw new InvalidMemberException("사용자 비밀번호 강도가 약합니다.");
		}
	}
}
