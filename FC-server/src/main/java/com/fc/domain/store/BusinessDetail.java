package com.fc.domain.store;

import com.fc.domain.member.Address;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessDetail {
	private String businessName;
	private String businessNumber;
	private Phone phone;
	private Address address;
}
