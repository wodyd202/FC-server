package com.fc.store;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.fc.command.store.SimpleStoreService;
import com.fc.command.store.StoreService;
import com.fc.command.store.exception.InvalidStoreException;
import com.fc.command.store.infra.StoreEventHandler;
import com.fc.command.store.infra.validator.ImageFileValidator;
import com.fc.command.store.infra.validator.StoreTagsValidator;
import com.fc.command.store.model.StoreCommand.ChangeOpeningHour;
import com.fc.command.store.model.StoreCommand.ChangeStoreImage;
import com.fc.command.store.model.StoreCommand.ChangeStoreStyle;
import com.fc.command.store.model.StoreCommand.ChangeStoreTag;
import com.fc.core.fileUploader.FileUploader;
import com.fc.core.infra.Validator;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Store;
import com.fc.query.member.infra.MemberRepository;

public class ChangeStoreServiceTest {
	
	MemberRepository memberRepository = mock(MemberRepository.class);
	StoreEventHandler storeEventHandler = mock(StoreEventHandler.class);
	StoreService storeService = new SimpleStoreService(memberRepository, storeEventHandler);
	
	Validator<ChangeStoreImage> imageValidator = new ImageFileValidator();
	
	Store store = mock(Store.class);

	@Test
	void 업체_영업시간_변경() {
		ChangeOpeningHour command = ChangeOpeningHour
				.builder()
				.build();
		Validator<ChangeOpeningHour> validator = new OpeningHourValidator();
		
		Owner targetStoreOwner = new Owner("email");
		storeService.changeWeekdayOpeningHour(validator, targetStoreOwner ,command);
		
		verify(store,times(1))
			.changeWeekdayOpeningHour(any());
	}
	
	@Test
	void 업체_스타일_변경() {
		ChangeStoreStyle command = new ChangeStoreStyle(Arrays.asList("스타일1","스타일2","스타일3"));
		Validator<ChangeStoreStyle> storeStyleValidator = new StoreStyleValidator();
		
		Owner targetStoreOwner = new Owner("email");
		storeService.changeStoreStyles(storeStyleValidator, targetStoreOwner, command);
		
		verify(store,times(1))
			.changeStyles(any());
	}
	
	@Test
	void 업체_태그_변경() {
		ChangeStoreTag command = new ChangeStoreTag(Arrays.asList("태그1","태그2","태그3"));
		Validator<ChangeStoreTag> storeTagsValidtor = new StoreTagsValidator();
		
		Owner targetStoreOwner = new Owner("email");
		storeService.changeStoreTags(storeTagsValidtor, targetStoreOwner ,command);
		
		verify(store,times(1))
				.changeTags(any());
	}
	
	@Test
	void 업체_이미지_변경_요청시_이미지_파일이_아니면_실패() {
		MultipartFile img = new MockMultipartFile("file.exe", new byte[] {});
		Owner targetStoreOwner = new Owner("email");
		ChangeStoreImage command = new ChangeStoreImage(img);
		
		assertThrows(InvalidStoreException.class, ()->{
			storeService.changeStoreImage(imageValidator,mock(FileUploader.class),targetStoreOwner,command);
		});
	}
	
	@Test
	void 업체_이미지_변경() {
		MultipartFile img = new MockMultipartFile("file.png", new byte[] {});
		Owner targetStoreOwner = new Owner("email");
		ChangeStoreImage command = new ChangeStoreImage(img);
		storeService.changeStoreImage(imageValidator,mock(FileUploader.class),targetStoreOwner,command);
		
		verify(store,times(1))
			.changeImage(any());
	}
	
	@BeforeEach
	void setUp() {
		when(storeEventHandler.find(any(Owner.class)))
			.thenReturn(Optional.of(store));
	}
}
