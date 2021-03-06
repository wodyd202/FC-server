package com.fc.command.store.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fc.command.common.address.model.AddressCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
		private int weekdayStartTime;
		private int weekdayEndTime;
		private int weekendStartTime;
		private int weekendEndTime;
		private List<String> holidays;
	}
	
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ChangeStoreInfo {
		private String businessName;
		private String businessNumber;
		private String phone;
		private AddressCommand address;
		private String addressDetail;
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ChangeStoreImage{
		private MultipartFile file;
	}
	
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ChangeStoreTag {
		private List<String> tags;
	}
	
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ChangeOpeningHour {
		private Integer weekdayStartTime;
		private Integer weekdayEndTime;
		private Integer weekendStartTime;
		private Integer weekendEndTime;
		private List<String> holidays;
	}
}
