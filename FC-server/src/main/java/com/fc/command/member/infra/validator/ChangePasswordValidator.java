package com.fc.command.member.infra.validator;

import com.fc.command.member.exception.InvalidMemberException;
import com.fc.command.member.model.MemberCommand.ChangePassword;
import com.fc.core.infra.Validator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChangePasswordValidator implements Validator<ChangePassword> {
	private final PasswordMeter passwordMeter;
	
	@Override
	public void validation(ChangePassword target) {
		String originPassword = target.getOriginPassword();
		String changePassword = target.getChangePassword();
		
		originPasswordValidation(originPassword);
		changePasswordValidation(changePassword);
		
		passwordMeter.meter(changePassword);
	}

	private void originPasswordValidation(String originPassword) {
		assertNotEmptyString(originPassword, new InvalidMemberException("기존 비밀번호를 입력해주세요."));
	}

	private void changePasswordValidation(String changePassword) {
		assertNotEmptyString(changePassword, new InvalidMemberException("변경할 비밀번호를 입력해주세요."));
	}
}
