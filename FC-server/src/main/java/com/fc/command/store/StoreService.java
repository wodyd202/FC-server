package com.fc.command.store;

import com.fc.command.common.address.infra.AddressDetailGetter;
import com.fc.command.store.model.StoreCommand.ChangeStoreInfo;
import com.fc.command.store.model.StoreCommand.CreateStore;
import com.fc.core.infra.Validator;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Store;

public interface StoreService {
	Store create(
			Validator<CreateStore> validator, 
			AddressDetailGetter addressGetter, 
			Owner targetOwner, 
			CreateStore command
		);

	Store changeStoreInfo(
			Validator<ChangeStoreInfo> validator, 
			AddressDetailGetter addressGetter, 
			Owner owner,
			ChangeStoreInfo command
		);
}
