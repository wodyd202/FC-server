package com.fc.domain.store.event;

import java.util.Date;

import com.fc.domain.store.BusinessDetail;
import com.fc.domain.store.OpeningHour;
import com.fc.domain.store.Owner;
import com.fc.domain.store.StoreTags;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterdStore extends AbstractStoreEvent{
	private static final long serialVersionUID = 1L;
	private BusinessDetail detail;
	private StoreTags tags;
	private OpeningHour openingHour;
	private Date createDateTime;
	
	public RegisterdStore(Owner owner, BusinessDetail detail, StoreTags tags, OpeningHour openingHour, Date createDateTime) {
		this.owner = owner;
		this.detail = detail;
		this.tags = tags;
		this.openingHour = openingHour;
		this.createDateTime = createDateTime;
	}
}
