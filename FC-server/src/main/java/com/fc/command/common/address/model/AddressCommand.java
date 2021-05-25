package com.fc.command.common.address.model;

import com.fc.command.member.model.MemberCommand.ChangeAddress;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressCommand {
	private double longtitude;
	private double letitude;
	
	public static AddressCommand of(ChangeAddress command) {
		AddressCommand address = new AddressCommand();
		address.longtitude = command.getLongtitude();
		address.letitude = command.getLetitude();
		return address;
	}
}
