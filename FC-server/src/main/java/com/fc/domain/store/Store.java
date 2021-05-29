package com.fc.domain.store;

import java.util.Date;
import java.util.List;

import com.fc.command.store.model.StoreCommand.CreateStore;
import com.fc.core.domain.AggregateRoot;
import com.fc.domain.member.Address;
import com.fc.domain.store.event.ChangedBusinessName;
import com.fc.domain.store.event.ChangedBusinessNumber;
import com.fc.domain.store.event.ChangedStoreAddress;
import com.fc.domain.store.event.ChangedStoreMainImage;
import com.fc.domain.store.event.ChangedStorePhone;
import com.fc.domain.store.event.ChangedStoreTags;
import com.fc.domain.store.event.ChangedWeekdayOpeningHour;
import com.fc.domain.store.event.ChangedWeekendOpeningHour;
import com.fc.domain.store.event.RegisterdStore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
  * @Date : 2021. 5. 26. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 aggregate
  */

@Getter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends AggregateRoot<Owner>{
	private static final long serialVersionUID = 1L;
	public enum StoreState { SELL, NOT_SELL , CLOSED }
	private Owner owner;
	private BusinessDetail detail;
	private StoreTags tags;
	private MainImage image;
	private OpeningHour openingHour;
	private StoreState state;
	private Date createDateTime;
	
	Store(Owner owner){
		super(owner);
		this.owner = owner;
	}
	
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
		super(owner);
		this.owner = owner;
		this.detail = new BusinessDetail(businessName, businessNumber, new Phone(phone), address, addressDetail);
		this.tags = new StoreTags(storeTags);
		this.openingHour = new OpeningHour(weekdayStartTime, weekdayEndTime, weekendStartTime, weekendEndTime, holidays);
		this.state = StoreState.SELL;
		this.createDateTime = new Date();
		applyChange(new RegisterdStore(owner, detail, tags, openingHour, createDateTime));
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
				.weekendStartTime(command.getWeekendStartTime())
				.weekendEndTime(command.getWeekendEndTime())
				.weekdayStartTime(command.getWeekdayStartTime())
				.weekdayEndTime(command.getWeekdayEndTime())
				.build();
	}
	
	public void changeAddress(Address realAddress, String addressDetail) {
		this.detail.changeAddress(realAddress, addressDetail);
		applyChange(new ChangedStoreAddress(this.owner,realAddress,addressDetail));
	}
	
	public void changePhone(String phone) {
		Phone changePhone = new Phone(phone);
		this.detail.changePhone(changePhone);
		applyChange(new ChangedStorePhone(this.owner, changePhone));
	}
	
	public void changeBusinessNumber(String businessNumber) {
		this.detail.changeBusinessNumber(businessNumber);
		applyChange(new ChangedBusinessNumber(this.owner, businessNumber));
	}
	
	public void changeBusinessName(String businessName) {
		this.detail.changeBusinessName(businessName);
		applyChange(new ChangedBusinessName(this.owner, businessName));
	}
	
	public void changeImage(String saveFileName) {
		this.image = new MainImage(saveFileName);
		applyChange(new ChangedStoreMainImage(this.owner, this.image));
	}

	public void changeTags(List<String> tags) {
		this.tags = new StoreTags(tags);
		applyChange(new ChangedStoreTags(this.owner, this.tags));
	}
	
	public void changeWeekdayOpeningHour(int startTime,int endTime) {
		this.openingHour.changeWeekdayOpeningHour(startTime, endTime);
		applyChange(new ChangedWeekdayOpeningHour(this.owner, startTime, endTime));
	}
	
	public void changeWeekendOpeningHour(int startTime,int endTime) {
		this.openingHour.changeWeekendOpeningHour(startTime, endTime);
		applyChange(new ChangedWeekendOpeningHour(this.owner, startTime, endTime));
	}
	
	protected void apply(RegisterdStore event) {
		this.owner = event.getIdentifier();
		this.detail = event.getDetail();
		this.tags = event.getTags();
		this.openingHour = event.getOpeningHour();
		this.createDateTime = event.getCreateDateTime();
		this.state = StoreState.SELL;
	}
	
	protected void apply(ChangedStoreAddress event) {
		this.detail.changeAddress(event.getAddress(), event.getAddressDetail());
	}
	
	protected void apply(ChangedStorePhone event) {
		this.detail.changePhone(event.getPhone());
	}
	
	protected void apply(ChangedBusinessNumber event) {
		this.detail.changeBusinessNumber(event.getBusinessNumber());
	}
	
	protected void apply(ChangedBusinessName event) {
		this.detail.changeBusinessName(event.getBusinessName());
	}
	
	protected void apply(ChangedStoreMainImage event) {
		this.image = event.getImage();
	}
	
}
