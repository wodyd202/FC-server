package com.fc.store;

import com.fc.command.store.infra.validator.AbstractStoreValidator;
import com.fc.command.store.model.StoreCommand.ChangeStoreTag;

public class StoreTagsValidator extends AbstractStoreValidator<ChangeStoreTag> {

	@Override
	public void validation(ChangeStoreTag target) {
		storeTagsValidation(target.getTags());
	}

}
