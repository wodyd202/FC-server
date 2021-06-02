package com.fc.query.store.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
	@JsonInclude(content = Include.NON_NULL)
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
		
		private Boolean interestState;
		
		public void addInterestState(boolean interestState) {
			this.interestState = interestState;
		}
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class StoreList {
		private List<StoreListData> storeLists;
		private long totalCount;
	}
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@JsonInclude(content = Include.NON_NULL)
	public static class StoreListData {
		private String businessTitle;
		private String storeTags;
		private String imagePath;
		
		private int weekdayStartTime;
		private int weekdayEndTime;
		private int weekendStartTime;
		private int weekendEndTime;
		
		private String holiday;
		
		private Boolean interestState;
		
		private long interestCnt;
		
		public void addInterestState(boolean interestState) {
			this.interestState = interestState;
		}
	}
}
