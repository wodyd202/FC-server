package com.fc.store;

import com.fc.command.member.AddressDetailGetter;
import com.fc.command.member.exception.MemberNotFoundException;
import com.fc.command.store.infra.StoreEventHandler;
import com.fc.command.store.model.StoreCommand.CreateStore;
import com.fc.core.infra.Validator;
import com.fc.domain.member.Address;
import com.fc.domain.member.Email;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Store;

import lombok.AllArgsConstructor;

/**
  * @Date : 2021. 5. 27. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 관련 service
  */
@AllArgsConstructor
public class SimpleStoreService implements StoreService {
	private MemberQueryRepository memberRepository;
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
		Address addressDetail = addressGetter.getDetail(command.getAddress());
		Store store = Store.create(targetOwner, addressDetail, command);
		storeEventHandler.save(store);
		return store;
	}

	private void verifyExistMember(Owner targetOwner) {
		memberRepository.findByEmail(new Email(targetOwner.getEmail())).orElseThrow(()->new MemberNotFoundException("해당 이메일의 회원이 존재하지 않습니다."));
	}

}
