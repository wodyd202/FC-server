package com.fc.service.member.model;

import com.fc.core.command.Command;
import com.fc.service.common.address.model.AddressCommand;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberCommand implements Command{
	private static final long serialVersionUID = 1;

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class CreateMemberCommand {
		private String email;
		private String password;
		private AddressCommand address;
		
		public CreateMemberCommand(String email, String password) {
			this.email = email;
			this.password = password;
		}
	}
}
