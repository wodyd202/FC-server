package com.fc.query.member.model;

import java.util.List;

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

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class InterestStoreList {
		private List<InterestStoreData> interestStoreList;
		private long totalCount;
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class InterestStoreData {
		private String owner;
		private String mainImage;
		private String businessName;
		private double longtitude;
		private double letitude;
		private String province;
		private String city;
		private String neighborhood;
		private String tags;
	}
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class InterestProductList {
		private List<InterestProductData> interestProductList;
		private long totalCount;
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class InterestProductData {
		private String mainImage;
		private String title;
		private int price;
		private String tags;
		private String storeName;
	}
}
