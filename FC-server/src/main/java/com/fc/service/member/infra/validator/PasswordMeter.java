package com.fc.service.member.infra.validator;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
public class PasswordMeter {
	@RequiredArgsConstructor
	public enum PasswordStrength {
		WEEK("약함"), 
		NOMAL("보통"), 
		STRONG("강함"), 
		INVALID("허용하지 않음");
		private final String value;
		public String getMsg() {
			return value;
		}
	}

	private final Set<Character> ALLOWED_SPECIAL_KEYWORD = new HashSet<Character>() {
		private static final long serialVersionUID = 1L;
		{
			add('.');
			add(',');
			add('/');
			add('?');
			add('`');
			add(';');
			add(':');
			add('[');
			add(']');
			add('\'');
		}
	};

	public PasswordStrength meter(String password) {
		return analysisPassword(password);
	}

	private PasswordStrength analysisPassword(String password) {
		int specialChar = 0;
		int upperCaseCnt = 0;
		int lowerCaseCnt = 0;

		if (!isAtLeast8And20OrLessPassword(password)) {
			return PasswordStrength.INVALID;
		}

		for (int i = 0; i < password.length(); i++) {
			char key = password.charAt(i);
			int keyCode = (int) password.charAt(i);
			if (isUpperCase(keyCode)) {
				upperCaseCnt++;
			} else if (isLowerCase(keyCode)) {
				lowerCaseCnt++;
			} else if (isSpecialChar(key)) {
				specialChar++;
			} else if (isNumberChar(keyCode)) {
			} else {
				return PasswordStrength.INVALID;
			}
		}
		return calcalateStrength(specialChar, upperCaseCnt, lowerCaseCnt);
	}

	private boolean isAtLeast8And20OrLessPassword(String password) {
		return password.length() >= 8 && password.length() <= 20;
	}

	private boolean isUpperCase(int keyCode) {
		return keyCode >= 65 && keyCode <= 90;
	}

	private boolean isLowerCase(int keyCode) {
		return keyCode >= 97 && keyCode <= 122;
	}

	private boolean isSpecialChar(char key) {
		return ALLOWED_SPECIAL_KEYWORD.contains(key);
	}

	private boolean isNumberChar(int keyCode) {
		return keyCode - '0' >= 0 && keyCode - '0' <= 9;
	}

	private PasswordStrength calcalateStrength(int specialChar, int upperCaseCnt, int lowerCaseCnt) {
		int result = 0;
		if (specialChar >= 3) {
			result++;
		}
		if (upperCaseCnt >= 3) {
			result++;
		}
		if (lowerCaseCnt >= 3) {
			result++;
		}

		if (result == 3) {
			return PasswordStrength.STRONG;
		} else if (result == 2) {
			return PasswordStrength.NOMAL;
		} else {
			return PasswordStrength.WEEK;
		}
	}

}
