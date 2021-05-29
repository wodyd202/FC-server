package com.fc.query.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StoreQuery {
	
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
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
