package com.fc.store;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.member.infra.validator.ChangeAddressValidator;
import com.fc.command.store.exception.InvalidStoreException;
import com.fc.command.store.infra.validator.CreateStoreValidator;
import com.fc.command.store.model.StoreCommand;
import com.fc.core.infra.Validator;

public class StoreValidatorTest {
	
	Validator<StoreCommand.CreateStore> validator = new CreateStoreValidator(mock(ChangeAddressValidator.class));
	
	@Test
	void 정상_케이스() {
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
		validator.validation(command);
	}
	
	@Test
	void 업체_휴일을_잘못입력했을때_실패() {
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
				.weekdayEndTime(18)
				.weekendStartTime(10)
				.weekendEndTime(18)
				.holidays(Arrays.asList("잘못된 값,수,목"))
				.build();
		assertThrows(InvalidStoreException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 업체_영업시간을_잘못입력했을때_실패() {
		StoreCommand.CreateStore command = StoreCommand.CreateStore
				.builder()
				.businessName("상호명")
				.businessNumber("1234567890")
				.phone("010-0000-0000")
				.address(new AddressCommand(1,1))
				.addressDetail("상세 주소")
				.storeTags(Arrays.asList("태그1","태그2","태그3"))
				.storeStyles(Arrays.asList("스타일1","스타일2"))
				.weekdayStartTime(30)
				.weekdayEndTime(38)
				.weekendStartTime(30)
				.weekendEndTime(38)
				.holidays(Arrays.asList("화","수","목"))
				.build();
		assertThrows(InvalidStoreException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 업체_평일_영업_시작_시간이_마감_시간보다_클때_실패() {
		StoreCommand.CreateStore command = StoreCommand.CreateStore
				.builder()
				.businessName("상호명")
				.businessNumber("1234567890")
				.phone("010-0000-0000")
				.address(new AddressCommand(1,1))
				.addressDetail("상세 주소")
				.storeTags(Arrays.asList("태그1","태그2","태그3"))
				.storeStyles(Arrays.asList("스타일1","스타일2"))
				.weekdayStartTime(20)
				.weekdayEndTime(18)
				.weekendStartTime(10)
				.weekendEndTime(18)
				.holidays(Arrays.asList("화","수","목"))
				.build();
		assertThrows(InvalidStoreException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 업체_태그_4개_이상_입력_실패() {
		StoreCommand.CreateStore command = StoreCommand.CreateStore
				.builder()
				.businessName("상호명")
				.businessNumber("1234567890")
				.phone("010-0000-0000")
				.address(new AddressCommand(1,1))
				.addressDetail("상세 주소")
				.storeTags(Arrays.asList("태그1","태그2","태그3","태그4"))
				.storeStyles(Arrays.asList("스타일1","스타일2"))
				.weekdayStartTime(10)
				.weekdayEndTime(18)
				.weekendStartTime(10)
				.weekendEndTime(18)
				.holidays(Arrays.asList("화","수","목"))
				.build();
		assertThrows(InvalidStoreException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 상세주소_누락_실패() {
		StoreCommand.CreateStore command = StoreCommand.CreateStore
				.builder()
				.businessName("상호명")
				.businessNumber("1234567890")
				.phone("010-0000-0000")
				.address(new AddressCommand(1,1))
//				.addressDetail("상세 주소")
				.storeTags(Arrays.asList("태그1","태그2","태그3"))
				.storeStyles(Arrays.asList("스타일1","스타일2"))
				.weekdayStartTime(10)
				.weekdayEndTime(18)
				.weekendStartTime(10)
				.weekendEndTime(18)
				.holidays(Arrays.asList("화","수","목"))
				.build();
		assertThrows(InvalidStoreException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 전화번호_누락_실패() {
		StoreCommand.CreateStore command = StoreCommand.CreateStore
				.builder()
				.businessName("상호명")
				.businessNumber("1234567890")
//				.phone("010-0000-0000")
				.address(new AddressCommand(1,1))
				.addressDetail("상세 주소")
				.storeTags(Arrays.asList("태그1","태그2","태그3"))
				.storeStyles(Arrays.asList("스타일1","스타일2"))
				.weekdayStartTime(10)
				.weekdayEndTime(18)
				.weekendStartTime(10)
				.weekendEndTime(18)
				.holidays(Arrays.asList("화","수","목"))
				.build();
		assertThrows(InvalidStoreException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 사업자_번호_누락_실패() {
		StoreCommand.CreateStore command = StoreCommand.CreateStore
				.builder()
				.businessName("상호명")
//				.businessNumber("1234567890")
				.phone("010-0000-0000")
				.address(new AddressCommand(1,1))
				.addressDetail("상세 주소")
				.storeTags(Arrays.asList("태그1","태그2","태그3"))
				.storeStyles(Arrays.asList("스타일1","스타일2"))
				.weekdayStartTime(10)
				.weekdayEndTime(18)
				.weekendStartTime(10)
				.weekendEndTime(18)
				.holidays(Arrays.asList("화","수","목"))
				.build();
		assertThrows(InvalidStoreException.class, ()->{
			validator.validation(command);
		});
	}
	
	@Test
	void 상호명_누락_실패() {
		StoreCommand.CreateStore command = StoreCommand.CreateStore
				.builder()
//				.businessName("상호명")
				.businessNumber("1234567890")
				.phone("010-0000-0000")
				.address(new AddressCommand(1,1))
				.addressDetail("상세 주소")
				.storeTags(Arrays.asList("태그1","태그2","태그3"))
				.storeStyles(Arrays.asList("스타일1","스타일2"))
				.weekdayStartTime(10)
				.weekdayEndTime(18)
				.weekendStartTime(10)
				.weekendEndTime(18)
				.holidays(Arrays.asList("화","수","목"))
				.build();
		assertThrows(InvalidStoreException.class, ()->{
			validator.validation(command);
		});
	}
}
