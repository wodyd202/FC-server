package com.fc.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fc.command.common.address.infra.AddressDetailGetter;
import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.member.exception.AlreadyDeletedMemberException;
import com.fc.command.member.infra.validator.ChangeAddressValidator;
import com.fc.command.store.SimpleStoreService;
import com.fc.command.store.StoreService;
import com.fc.command.store.exception.AlreadyExistStoreException;
import com.fc.command.store.infra.StoreEventHandler;
import com.fc.command.store.infra.StoreStyleRepository;
import com.fc.command.store.infra.StoreTagRepository;
import com.fc.command.store.infra.validator.CreateStoreValidator;
import com.fc.command.store.model.StoreCommand;
import com.fc.command.store.model.StoreCommand.ChangeStoreInfo;
import com.fc.command.store.model.StoreCommand.CreateStore;
import com.fc.core.infra.Validator;
import com.fc.domain.member.Address;
import com.fc.domain.member.Email;
import com.fc.domain.member.read.Member;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Store;
import com.fc.query.member.infra.MemberRepository;

@SuppressWarnings("unchecked")
public class StoreServiceTest {
	StoreTagRepository storeTagRepository = mock(StoreTagRepository.class);
	StoreStyleRepository storeStyleRepository = mock(StoreStyleRepository.class);
	
	Validator<CreateStore> validator 
				= new CreateStoreValidator(storeTagRepository,storeStyleRepository,new ChangeAddressValidator());
	
	StoreEventHandler storeEventHandler = mock(StoreEventHandler.class);
	AddressDetailGetter addressGetter = mock(AddressDetailGetter.class);
	
	MemberRepository memberRepository = mock(MemberRepository.class);
	
	StoreService storeService = new SimpleStoreService(memberRepository, storeEventHandler);

	Member mockMember = mock(Member.class);
	
	@Test
	void 업체_정보_모두_변경() {
		ChangeStoreInfo command = StoreCommand.ChangeStoreInfo
				.builder()
				.address(mock(AddressCommand.class))
				.addressDetail("상세 주소 변경")
				.businessName("상호명 변경")
				.businessNumber("사업자 번호")
				.phone("전화번호 변경")
				.build();
			
			Store mockStore = mock(Store.class);
			
			when(storeEventHandler.find(any()))
				.thenReturn(Optional.of(mockStore));
			
			storeService.changeStoreInfo(mock(Validator.class), addressGetter, new Owner("test@naver.com"), command);
			
			verify(mockStore,times(1))
				.changeBusinessName(any());
			verify(mockStore,times(1))
				.changeAddress(any(),any());
			verify(mockStore,times(1))
				.changeBusinessNumber(any());
			verify(mockStore,times(1))
				.changePhone(any());
	}
	
	@Test
	void 업체_상호명_및_주소_사업자번호_변경() {
		ChangeStoreInfo command = StoreCommand.ChangeStoreInfo
				.builder()
				.address(mock(AddressCommand.class))
				.addressDetail("상세 주소 변경")
				.businessName("상호명 변경")
				.businessNumber("사업자 번호")
				.build();
			
			Store mockStore = mock(Store.class);
			
			when(storeEventHandler.find(any()))
				.thenReturn(Optional.of(mockStore));
			
			storeService.changeStoreInfo(mock(Validator.class), addressGetter, new Owner("test@naver.com"), command);
			
			verify(mockStore,times(1))
				.changeBusinessName(any());
			verify(mockStore,times(1))
				.changeAddress(any(),any());
			verify(mockStore,times(1))
				.changeBusinessNumber(any());
			verify(mockStore,never())
				.changePhone(any());
	}
	
	@Test
	void 업체_상호명_및_주소_변경() {
		ChangeStoreInfo command = StoreCommand.ChangeStoreInfo
				.builder()
				.address(mock(AddressCommand.class))
				.addressDetail("상세 주소 변경")
				.businessName("상호명 변경")
				.build();
			
			Store mockStore = mock(Store.class);
			
			when(storeEventHandler.find(any()))
				.thenReturn(Optional.of(mockStore));
			
			storeService.changeStoreInfo(mock(Validator.class), addressGetter, new Owner("test@naver.com"), command);
			
			verify(mockStore,times(1))
				.changeBusinessName(any());
			verify(mockStore,times(1))
				.changeAddress(any(),any());
			verify(mockStore,never())
				.changeBusinessNumber(any());
			verify(mockStore,never())
				.changePhone(any());
	}
	
	@Test
	void 업체_상호명만_변경() {
		ChangeStoreInfo command = StoreCommand.ChangeStoreInfo
			.builder()
			.businessName("상호명 변경")
			.build();
		
		Store mockStore = mock(Store.class);
		
		when(storeEventHandler.find(any()))
			.thenReturn(Optional.of(mockStore));
		
		storeService.changeStoreInfo(mock(Validator.class), addressGetter, new Owner("test@naver.com"), command);
		
		verify(mockStore,times(1))
			.changeBusinessName(any());
		verify(mockStore,never())
			.changeAddress(any(),any());
		verify(mockStore,never())
			.changeBusinessNumber(any());
		verify(mockStore,never())
			.changePhone(any());
	}
	
	@Test
	void 이미_탈퇴한_회원이_업체를_등록할때_실패() {
		when(mockMember.isDelete())
			.thenReturn(true);
		
		assertThrows(AlreadyDeletedMemberException.class, ()->{
			storeService.create(mock(Validator.class), addressGetter, new Owner("test@naver.com"), mock(CreateStore.class));
		});
	}
	
	@Test
	void 업체_등록시_이미_등록된_업체가있다면_실패() {
		when(storeEventHandler.find(new Owner("test@naver.com")))
			.thenReturn(Optional.ofNullable(mock(Store.class)));
		
		assertThrows(AlreadyExistStoreException.class, ()->{
			storeService.create(mock(Validator.class), addressGetter, new Owner("test@naver.com"), mock(CreateStore.class));
		});
	}
	
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
		
		when(addressGetter.getDetail(any(AddressCommand.class)))
			.thenReturn(mock(Address.class));
		
		Store store = storeService.create(validator, addressGetter, new Owner("test@naver.com") ,command);
		
		assertThat(store).isNotNull();
	}
	
	@BeforeEach
	void setUp() {
		when(memberRepository.findByEmail(any(Email.class)))
			.thenReturn(Optional.of(mockMember));
		
		when(storeTagRepository.existByTagName(any()))
			.thenReturn(true);
		
		when(storeStyleRepository.existByStyleName(any()))
			.thenReturn(true);
	}
}
