package com.fc.store;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import com.fc.command.store.SimpleStoreService;
import com.fc.command.store.StoreService;
import com.fc.command.store.infra.StoreEventHandler;
import com.fc.command.store.infra.StoreTagRepository;
import com.fc.command.store.infra.validator.ImageFileValidator;
import com.fc.command.store.infra.validator.OpeningHourValidator;
import com.fc.command.store.infra.validator.StoreTagsValidator;
import com.fc.command.store.model.StoreCommand.ChangeOpeningHour;
import com.fc.command.store.model.StoreCommand.ChangeStoreImage;
import com.fc.command.store.model.StoreCommand.ChangeStoreTag;
import com.fc.core.infra.Validator;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Store;
import com.fc.query.member.infra.MemberRepository;

public class ChangeStoreServiceTest {
	MemberRepository memberRepository = mock(MemberRepository.class);
	StoreEventHandler storeEventHandler = mock(StoreEventHandler.class);
	StoreService storeService = new SimpleStoreService(mock(ApplicationEventPublisher.class), memberRepository, storeEventHandler);
	
	Validator<ChangeStoreImage> imageValidator = new ImageFileValidator();
	
	Store store = mock(Store.class);

	@Test
	void 업체_영업시간_변경() {
		ChangeOpeningHour command = ChangeOpeningHour
				.builder()
				.weekdayStartTime(3)
				.weekdayEndTime(24)
				.build();
		Validator<ChangeOpeningHour> validator = new OpeningHourValidator();
		
		Owner targetStoreOwner = new Owner("email");
		storeService.changeOpeningHour(validator, targetStoreOwner ,command);
		
		verify(store,times(1))
			.changeWeekdayOpeningHour(any(Integer.class),any(Integer.class));
	}
	
	@Test
	void 업체_태그_변경() {
		StoreTagRepository storeTagRepository = mock(StoreTagRepository.class);
	
		when(storeTagRepository.existByTagName(any()))
			.thenReturn(true);
		
		ChangeStoreTag command = new ChangeStoreTag(Arrays.asList("태그1","태그2","태그3"));
		Validator<ChangeStoreTag> storeTagsValidtor = new StoreTagsValidator(storeTagRepository);
		
		Owner targetStoreOwner = new Owner("email");
		storeService.changeStoreTags(storeTagsValidtor, targetStoreOwner ,command);
		
		verify(store,times(1))
			.changeTags(any());
	}
	
	@BeforeEach
	void setUp() {
		when(storeEventHandler.find(any(Owner.class)))
			.thenReturn(Optional.of(store));
	}
}
