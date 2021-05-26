package com.fc.domain.store;

import java.util.Date;
import java.util.List;

import com.fc.command.store.model.StoreCommand.CreateStore;
import com.fc.core.domain.AggregateRoot;
import com.fc.domain.member.Address;
import com.fc.domain.store.event.RegisterdStore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
  * @Date : 2021. 5. 26. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 aggregate
  */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends AggregateRoot<Owner>{
	private static final long serialVersionUID = 1L;
	public enum StoreState { SELL, NOT_SELL , CLOSED }
	private Owner owner;
	private BusinessDetail detail;
	private StoreTags tags;
	private StoreStyles styles;
	private MainImage image;
	private OpeningHour openingHour;
	private StoreState state;
	private Date createDateTime;
	
	@Builder
	private Store(
			Owner owner,
			String businessName,
			String businessNumber, 
			String phone, 
			Address address, 
			String addressDetail,
			List<String> storeTags,
			List<String> storeStyles,
			int weekdayStartTime,
			int weekdayEndTime,
			int weekendStartTime,
			int weekendEndTime,
			List<String> holidays
		) {
		this.owner = owner;
		this.detail = new BusinessDetail(businessName, businessNumber, new Phone(phone), address, addressDetail);
		this.tags = new StoreTags(storeTags);
		this.styles = new StoreStyles(storeStyles);
		this.openingHour = new OpeningHour(weekdayStartTime, weekdayEndTime, weekendStartTime, weekendEndTime, holidays);
		this.state = StoreState.SELL;
		this.createDateTime = new Date();
		applyChange(new RegisterdStore(owner, detail, tags, styles, openingHour, createDateTime));
	}
	
	public static Store create(Owner targetOwner, Address storeAddress, CreateStore command) {
		return Store.builder()
				.owner(targetOwner)
				.businessName(command.getBusinessName())
				.businessNumber(command.getBusinessNumber())
				.phone(command.getPhone())
				.address(storeAddress)
				.addressDetail(command.getAddressDetail())
				.storeTags(command.getStoreTags())
				.storeStyles(command.getStoreStyles())
				.weekendStartTime(command.getWeekendStartTime())
				.weekendEndTime(command.getWeekendEndTime())
				.weekdayStartTime(command.getWeekdayStartTime())
				.weekdayEndTime(command.getWeekdayEndTime())
				.build();
	}
	
	protected void apply(RegisterdStore event) {
		this.owner = event.getIdentifier();
		this.detail = event.getDetail();
		this.tags = event.getTags();
		this.styles = event.getStyles();
		this.openingHour = event.getOpeningHour();
		this.createDateTime = event.getCreateDateTime();
		this.state = StoreState.SELL;
	}
}
