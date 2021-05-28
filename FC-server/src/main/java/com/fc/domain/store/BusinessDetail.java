package com.fc.domain.store;

import javax.persistence.Embedded;

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
	
	@Embedded
	private Address address;
	private String addressDetail;
	
	public void changeAddress(Address address, String addressDetail) {
		this.address = address;
		this.addressDetail = addressDetail;
	}

	public void changePhone(Phone phone) {
		this.phone = phone;
	}

	public void changeBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	public void changeBusinessName(String businessName) {
		this.businessName = businessName;
	}
}
