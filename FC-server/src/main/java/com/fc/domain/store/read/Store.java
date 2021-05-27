package com.fc.domain.store.read;

import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

import com.fc.domain.member.Address;
import com.fc.domain.store.BusinessDetail;
import com.fc.domain.store.MainImage;
import com.fc.domain.store.OpeningHour;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Phone;
import com.fc.domain.store.Store.StoreState;
import com.fc.domain.store.StoreStyle;
import com.fc.domain.store.StoreTag;
import com.fc.domain.store.StoreTags;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STORE")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Store {

	@EmbeddedId
	@AttributeOverride(column = @Column(name = "owner"), name = "email")
	private Owner owner;

	@Embedded
	private BusinessDetail detail;

	@ElementCollection
	@CollectionTable(name = "STORE_TAGS", joinColumns = @JoinColumn(name = "STORE_OWNER", referencedColumnName = "owner"))
	private Set<StoreTag> tags;

	@ElementCollection
	@CollectionTable(name = "STORE_STYLES", joinColumns = @JoinColumn(name = "STORE_OWNER", referencedColumnName = "owner"))
	private Set<StoreStyle> styles;

	@Embedded
	private MainImage image;

	@Embedded
	private OpeningHour openingHour;

	@Enumerated(EnumType.STRING)
	private StoreState state;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	public void changeBusinessName(String businessName) {
		this.changeBusinessName(businessName);
	}

	public void changeBusinessNumber(String businessNumber) {
		this.changeBusinessNumber(businessNumber);
	}

	public void changeAddress(Address address, String addressDetail) {
		this.detail.changeAddress(address, addressDetail);
	}

	public void changePhone(Phone phone) {
		this.detail.changePhone(phone);
	}

	public void changeImage(MainImage image) {
		this.image = image;
	}

	public void changeTags(StoreTags tags) {
		this.tags = tags.getTags();
	}
}
