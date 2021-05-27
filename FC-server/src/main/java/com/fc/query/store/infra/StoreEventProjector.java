package com.fc.query.store.infra;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fc.core.event.AbstractEventProjector;
import com.fc.domain.store.Store.StoreState;
import com.fc.domain.store.event.ChangedBusinessName;
import com.fc.domain.store.event.ChangedBusinessNumber;
import com.fc.domain.store.event.ChangedStoreAddress;
import com.fc.domain.store.event.ChangedStoreMainImage;
import com.fc.domain.store.event.ChangedStorePhone;
import com.fc.domain.store.event.ChangedStoreStyles;
import com.fc.domain.store.event.ChangedStoreTags;
import com.fc.domain.store.event.ChangedWeekdayOpeningHour;
import com.fc.domain.store.event.ChangedWeekendOpeningHour;
import com.fc.domain.store.event.RegisterdStore;
import com.fc.domain.store.read.Store;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class StoreEventProjector extends AbstractEventProjector{
	private final StoreJpaRepository storeJpaRepository;
	
	protected void execute(ChangedBusinessName event) {
		Store store = storeJpaRepository.findById(event.getIdentifier()).get();
		store.changeBusinessName(event.getBusinessName());
		storeJpaRepository.save(store);
	}
	
	protected void execute(ChangedBusinessNumber event) {
		Store store = storeJpaRepository.findById(event.getIdentifier()).get();
		store.changeBusinessNumber(event.getBusinessNumber());
		storeJpaRepository.save(store);
	}
	
	protected void execute(ChangedStoreAddress event) {
		Store store = storeJpaRepository.findById(event.getIdentifier()).get();
		store.changeAddress(event.getAddress(),event.getAddressDetail());
		storeJpaRepository.save(store);
	}
	
	protected void execute(ChangedStoreMainImage event) {
		Store store = storeJpaRepository.findById(event.getIdentifier()).get();
		store.changeImage(event.getImage());
		storeJpaRepository.save(store);
	}
	
	protected void execute(ChangedStorePhone event) {
		Store store = storeJpaRepository.findById(event.getIdentifier()).get();
		store.changePhone(event.getPhone());
		storeJpaRepository.save(store);
	}
	
	protected void execute(ChangedStoreTags event) {
		Store store = storeJpaRepository.findById(event.getIdentifier()).get();
		store.changeTags(event.getTags());
		storeJpaRepository.save(store);
	}
	
	protected void execute(ChangedStoreStyles event) {
		Store store = storeJpaRepository.findById(event.getIdentifier()).get();
		store.changeStyles(event.getStyles());
		storeJpaRepository.save(store);
	}
	
	protected void execute(ChangedWeekdayOpeningHour event) {
		Store store = storeJpaRepository.findById(event.getIdentifier()).get();
		store.changeWeekdayOpeningHour();
		storeJpaRepository.save(store);
		/**
		 * TODO changedWeekdayOpeningHour event execute
		 */
	}
	
	protected void execute(ChangedWeekendOpeningHour event) {
		/**
		 * TODO changedWeekendOpeningHour event execute
		 */
	}
	
	protected void execute(RegisterdStore event) {
		Store store = Store.builder()
			.owner(event.getOwner())
			.detail(event.getDetail())
			.tags(event.getTags().getTags())
			.styles(event.getStyles().getStyles())
			.openingHour(event.getOpeningHour())
			.state(StoreState.SELL)
			.createDateTime(event.getCreateDateTime())
			.build();
		storeJpaRepository.save(store);
	}
	
}
