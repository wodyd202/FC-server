package com.fc.command.member.model;

import com.fc.core.command.Command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberCommand implements Command{
	private static final long serialVersionUID = 1;

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class CreateMember {
		private String email;
		private String password;
	}
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ChangeAddress {
		private double longtitude;
		private double letitude;
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ChangePassword {
		private String originPassword;
		private String changePassword;
	}
	
}
