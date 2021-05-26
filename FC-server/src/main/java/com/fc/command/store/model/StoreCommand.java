package com.fc.command.store.model;

import java.util.List;

import com.fc.command.common.address.model.AddressCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StoreCommand {
	
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CreateStore {
		private String businessName;
		private String businessNumber;
		private String phone;
		private AddressCommand address;
		private String addressDetail;
		private List<String> storeTags;
		private List<String> storeStyles;
		private int weekdayStartTime;
		private int weekdayEndTime;
		private int weekendStartTime;
		private int weekendEndTime;
		private List<String> holidays;
	}
}
