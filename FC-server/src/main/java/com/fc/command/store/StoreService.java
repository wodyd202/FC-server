package com.fc.command.store;

import com.fc.command.common.address.infra.AddressDetailGetter;
import com.fc.command.store.model.StoreCommand.ChangeOpeningHour;
import com.fc.command.store.model.StoreCommand.ChangeStoreImage;
import com.fc.command.store.model.StoreCommand.ChangeStoreInfo;
import com.fc.command.store.model.StoreCommand.ChangeStoreStyle;
import com.fc.command.store.model.StoreCommand.ChangeStoreTag;
import com.fc.command.store.model.StoreCommand.CreateStore;
import com.fc.core.fileUploader.FileUploader;
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

	void changeStoreImage(
			Validator<ChangeStoreImage> validator, 
			FileUploader fileUploader,
			Owner targetStoreOwner, 
			ChangeStoreImage command
		);

	void changeStoreTags(
			Validator<ChangeStoreTag> storeTagsValidtor, 
			Owner targetStoreOwner, 
			ChangeStoreTag command
		);

	void changeStoreStyles(
			Validator<ChangeStoreStyle> storeStyleValidator, 
			Owner targetStoreOwner,
			ChangeStoreStyle command
		);

	void changeWeekdayOpeningHour(
			Validator<ChangeOpeningHour> validator, 
			Owner targetStoreOwner,
			ChangeOpeningHour command
		);
}
