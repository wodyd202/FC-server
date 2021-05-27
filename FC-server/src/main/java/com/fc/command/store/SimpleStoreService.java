package com.fc.command.store;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fc.command.common.address.infra.AddressDetailGetter;
import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.member.exception.AlreadyDeletedMemberException;
import com.fc.command.member.exception.MemberNotFoundException;
import com.fc.command.store.exception.AlreadyExistStoreException;
import com.fc.command.store.exception.StoreNotFoundException;
import com.fc.command.store.infra.StoreEventHandler;
import com.fc.command.store.model.StoreCommand.ChangeStoreImage;
import com.fc.command.store.model.StoreCommand.ChangeStoreInfo;
import com.fc.command.store.model.StoreCommand.ChangeStoreTag;
import com.fc.command.store.model.StoreCommand.CreateStore;
import com.fc.core.fileUploader.FileUploader;
import com.fc.core.infra.Validator;
import com.fc.domain.member.Address;
import com.fc.domain.member.Email;
import com.fc.domain.member.read.Member;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Store;
import com.fc.query.member.infra.MemberRepository;

import lombok.AllArgsConstructor;

/**
  * @Date : 2021. 5. 27. 
  * @작성자 : LJY
  * @프로그램 설명 :
  */
@AllArgsConstructor
public class SimpleStoreService implements StoreService {
	private MemberRepository memberRepository;
	private StoreEventHandler storeEventHandler;
	
	@Override
	public Store create(
			Validator<CreateStore> validator, 
			AddressDetailGetter addressGetter,
			Owner targetOwner, 
			CreateStore command
		) {
		validator.validation(command);
		verifyExistMember(targetOwner);
		verifyNotExistStore(targetOwner);
		Address addressDetail = addressGetter.getDetail(command.getAddress());
		Store store = Store.create(targetOwner, addressDetail, command);
		storeEventHandler.save(store);
		return store;
	}

	@Override
	public Store changeStoreInfo(
			Validator<ChangeStoreInfo> validator, 
			AddressDetailGetter addressGetter, 
			Owner targetOwner,
			ChangeStoreInfo command
		) {
		validator.validation(command);
		verifyExistMember(targetOwner);
		Store findStore = storeEventHandler.find(targetOwner)
					.orElseThrow(()->new StoreNotFoundException("해당 회원의 업체 정보가 존재하지 않습니다."));
		
		AddressCommand address = command.getAddress();
		String addressDetail = command.getAddressDetail();
		String businessName = command.getBusinessName();
		String businessNumber = command.getBusinessNumber();
		String phone = command.getPhone();
		
		if(address != null && addressDetail != null) {
			Address realAddress = addressGetter.getDetail(address);
			findStore.changeAddress(realAddress,addressDetail);
		}
		
		if(businessName != null) {
			findStore.changeBusinessName(businessName);
		}
		
		if(businessNumber != null) {
			findStore.changeBusinessNumber(businessNumber);
		}
		
		if(phone != null) {
			findStore.changePhone(phone);
		}
		
		storeEventHandler.save(findStore);
		return findStore;
	}
	
	private void verifyExistMember(Owner targetOwner) {
		Member findMember = memberRepository.findByEmail(new Email(targetOwner.getEmail()))
			.orElseThrow(()->new MemberNotFoundException("해당 이메일의 회원이 존재하지 않습니다."));
		if(findMember.isDelete()) {
			throw new AlreadyDeletedMemberException("이미 탈퇴한 회원입니다.");
		}
	}
	
	private void verifyNotExistStore(Owner targetOwner) {
		storeEventHandler.find(targetOwner).ifPresent((store)->{
			throw new AlreadyExistStoreException("해당 회원의 업체정보가 이미 존재합니다.");
		});
	}

	@Override
	public void changeStoreImage(
			Validator<ChangeStoreImage> validator,
			FileUploader fileUploader,
			Owner targetOwner,
			ChangeStoreImage command
		) {
		validator.validation(command);
		Store findStore = storeEventHandler.find(targetOwner)
				.orElseThrow(()->new StoreNotFoundException("해당 회원의 업체 정보가 존재하지 않습니다."));
		
		String saveFileName = UUID.randomUUID() + getFileExtention(command.getFile());
		findStore.changeImage(saveFileName);
		
		fileUploader.uploadFile(command.getFile(), saveFileName);
		storeEventHandler.save(findStore);
	}
	
	private String getFileExtention(MultipartFile file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		return name.substring(lastIndexOf, name.length()).toUpperCase();
	}

	@Override
	public void changeStoreTags(
			Validator<ChangeStoreTag> validator, 
			Owner targetStoreOwner,
			ChangeStoreTag command
		) {
		validator.validation(command);
		Store findStore = storeEventHandler.find(targetStoreOwner)
				.orElseThrow(()->new StoreNotFoundException("해당 회원의 업체 정보가 존재하지 않습니다."));
		findStore.changeTags(command.getTags());
		storeEventHandler.save(findStore);
	}
	
}
