package com.fc.store;

import com.fc.command.member.AddressDetailGetter;
import com.fc.command.store.model.StoreCommand.CreateStore;
import com.fc.core.infra.Validator;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Store;

public interface StoreService {
	Store create(Validator<CreateStore> validator, AddressDetailGetter addressGetter, Owner targetOwner, CreateStore command);
}
