package com.fc.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.member.AddressDetailGetter;
import com.fc.command.member.infra.validator.ChangeAddressValidator;
import com.fc.command.store.infra.StoreEventHandler;
import com.fc.command.store.infra.validator.CreateStoreValidator;
import com.fc.command.store.model.StoreCommand;
import com.fc.command.store.model.StoreCommand.CreateStore;
import com.fc.core.infra.Validator;
import com.fc.domain.member.Address;
import com.fc.domain.member.Email;
import com.fc.domain.member.read.Member;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Store;

public class StoreServiceTest {
	
	@Test
	void 업체_등록() {
		StoreCommand.CreateStore command = StoreCommand.CreateStore
				.builder()
				.businessName("상호명")
				.businessNumber("1234567890")
				.phone("010-0000-0000")
				.address(new AddressCommand(1,1))
				.addressDetail("상세 주소")
				.storeTags(Arrays.asList("태그1","태그2","태그3"))
				.storeStyles(Arrays.asList("스타일1","스타일2"))
				.weekdayStartTime(10)
				.weekdayEndTime(14)
				.weekendStartTime(10)
				.weekendEndTime(14)
				.holidays(Arrays.asList("화","수","목"))
				.build();
		
		Validator<CreateStore> validator = new CreateStoreValidator(new ChangeAddressValidator());
		StoreEventHandler storeEventHandler = mock(StoreEventHandler.class);
		AddressDetailGetter addressGetter = mock(AddressDetailGetter.class);
		
		MemberQueryRepository memberQueryRepository = mock(MemberQueryRepository.class);
		
		Member mockMember = mock(Member.class);
		
		when(memberQueryRepository.findByEmail(any(Email.class)))
			.thenReturn(Optional.of(mockMember));
		
		when(addressGetter.getDetail(any(AddressCommand.class)))
			.thenReturn(mock(Address.class));
		
		StoreService storeService = new SimpleStoreService(memberQueryRepository, storeEventHandler);
		Store store = storeService.create(validator, addressGetter, new Owner("test@naver.com") ,command);
		
		assertThat(store).isNotNull();
	}
}
