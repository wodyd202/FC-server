package com.fc.query.store.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StoreQuery {
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class StoreMainInfo {
		private String businessTitle;
		
		private double longtitude;
		private double letitude;
		private String province;
		private String city;
		private String neighborhood;
		
		private String phone;
		
		private int weekdayStartTime;
		private int weekdayEndTime;
		private int weekendStartTime;
		private int weekendEndTime;
		
		private String holiday;
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class StoreList {
		private String businessTitle;
		private String storeTags;
		private String imagePath;
		
		private int weekdayStartTime;
		private int weekdayEndTime;
		private int weekendStartTime;
		private int weekendEndTime;
		
		private String holiday;
		
		private long interestCnt;
	}
}
