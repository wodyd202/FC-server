package com.fc.command.store.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fc.command.common.address.infra.AddressDetailGetter;
import com.fc.command.store.StoreService;
import com.fc.command.store.infra.validator.ChangeStoreInfoValidator;
import com.fc.command.store.infra.validator.CreateStoreValidator;
import com.fc.command.store.infra.validator.ImageFileValidator;
import com.fc.command.store.infra.validator.OpeningHourValidator;
import com.fc.command.store.infra.validator.StoreStyleValidator;
import com.fc.command.store.infra.validator.StoreTagsValidator;
import com.fc.command.store.model.StoreCommand;
import com.fc.config.security.LoginUser;
import com.fc.core.fileUploader.FileUploader;
import com.fc.domain.member.read.Member;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Store;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/store")
@Api(tags = "업체 상태 관련 API")
public class StoreCommandApi {
	private StoreService storeService;
	private ChangeStoreInfoValidator changeStoreInfoValidator; 
	private CreateStoreValidator createStoreValidator; 
	private ImageFileValidator imageFileValidator;
	private OpeningHourValidator openingHourValidator; 
	private StoreStyleValidator storeStyleValidator;
	private StoreTagsValidator storeTagsValidator;
	private AddressDetailGetter addressGetter;
	private FileUploader fileUploader;
	
	@ApiOperation("업체 등록")
	@PostMapping
	public ResponseEntity<Store> execute(
			@RequestBody StoreCommand.CreateStore command, 
			@LoginUser Member loginMember
		){
		final Store createStore = storeService.create(createStoreValidator, addressGetter, Owner.withMember(loginMember), command);
		return new ResponseEntity<>(createStore, HttpStatus.CREATED);
	}
	
	@ApiOperation("업체 기본정보 변경")
	@PutMapping("store-info")
	public ResponseEntity<Store> execute(
			@RequestBody StoreCommand.ChangeStoreInfo command,
			@LoginUser Member loginMember
		){
		Store changeStoreInfo = storeService.changeStoreInfo(changeStoreInfoValidator, addressGetter, Owner.withMember(loginMember), command);
		return new ResponseEntity<>(changeStoreInfo, HttpStatus.OK);
	}
	
	@ApiOperation("업체 이미지 변경")
	@PutMapping("store-image")
	public ResponseEntity<Store> execute(
			@RequestBody StoreCommand.ChangeStoreImage command,
			@LoginUser Member loginMember
		){
		Store changeStoreImage = storeService.changeStoreImage(imageFileValidator, fileUploader, Owner.withMember(loginMember), command);	
		return new ResponseEntity<>(changeStoreImage, HttpStatus.OK);
	}
	
	@ApiOperation("업체 태그 변경")
	@PutMapping("tags")
	public ResponseEntity<Store> execute(
			@RequestBody StoreCommand.ChangeStoreTag command,
			@LoginUser Member loginMember
		){
		Store changeStoreTags = storeService.changeStoreTags(storeTagsValidator, Owner.withMember(loginMember), command);
		return new ResponseEntity<>(changeStoreTags, HttpStatus.OK);
	}
	
	@ApiOperation("업체 스타일 변경")
	@PutMapping("styles")
	public ResponseEntity<Store> execute(
			@RequestBody StoreCommand.ChangeStoreStyle command,
			@LoginUser Member loginMember
		){
		Store changeStoreStyles = storeService.changeStoreStyles(storeStyleValidator, Owner.withMember(loginMember), command);
		return new ResponseEntity<>(changeStoreStyles, HttpStatus.OK);
	}
	
	@ApiOperation("업체 운영시간 변경")
	@PutMapping("opening-hour")
	public ResponseEntity<Store> execute(
			@RequestBody StoreCommand.ChangeOpeningHour command,
			@LoginUser Member loginMember
		){
		Store changeOpeningHour = storeService.changeOpeningHour(openingHourValidator, Owner.withMember(loginMember), command);
		return new ResponseEntity<>(changeOpeningHour, HttpStatus.OK);
	}
	
}
