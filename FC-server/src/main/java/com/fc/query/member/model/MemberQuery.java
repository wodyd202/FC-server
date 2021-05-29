package com.fc.query.member.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberQuery {
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Address {
		private double longtitude;
		private double letitude;
		private String province;
		private String city;
		private String neighborhood;
	}
}
