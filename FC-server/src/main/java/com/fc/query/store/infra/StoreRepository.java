package com.fc.query.store.infra;

import java.util.Optional;

import com.fc.domain.store.Owner;
import com.fc.domain.store.read.Store;

public interface StoreRepository {
	Optional<Store> findByOwner(Owner owner);
}
