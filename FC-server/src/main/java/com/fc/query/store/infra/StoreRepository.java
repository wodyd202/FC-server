package com.fc.query.store.infra;

import java.util.List;
import java.util.Optional;

import com.fc.domain.store.Owner;
import com.fc.domain.store.read.Store;
import com.fc.query.store.api.StoreSearch;

public interface StoreRepository {
	Optional<Store> findByOwner(Owner owner);

	Optional<Store> findDetailByOwner(Owner owner);

	List<Store> findAll(StoreSearch dto);
}
