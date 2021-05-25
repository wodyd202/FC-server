package com.fc.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	private double longtitude;
	private double letitude;
	
	// 도
	private String province;
	
	// 시
	private String city;
	
	// 동
	private String neighborhood;
}
