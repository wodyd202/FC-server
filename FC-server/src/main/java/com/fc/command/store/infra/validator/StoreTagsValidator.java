package com.fc.command.store.infra.validator;

import com.fc.command.store.infra.StoreTagRepository;
import com.fc.command.store.model.StoreCommand.ChangeStoreTag;

public class StoreTagsValidator extends AbstractStoreValidator<ChangeStoreTag> {

	@Override
	public void validation(ChangeStoreTag target) {
		storeTagsValidation(target.getTags());
	}
	
	public StoreTagsValidator(StoreTagRepository storeTagRepository) {
		super(storeTagRepository);
	}
}
