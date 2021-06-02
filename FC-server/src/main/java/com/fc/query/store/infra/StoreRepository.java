package com.fc.query.store.infra;

import java.util.Optional;

import com.fc.domain.member.read.Member;
import com.fc.domain.store.Owner;
import com.fc.domain.store.read.Store;
import com.fc.query.store.model.StoreQuery;
import com.fc.query.store.model.StoreSearch;

public interface StoreRepository {
	Optional<StoreQuery.StoreMainInfo> findByOwner(Owner owner, Member loginMember);

	Optional<Store> findDetailByOwner(Owner owner);

	boolean existByOnwer(Owner owner);
	
	StoreQuery.StoreList findAll(StoreSearch dto, Member loginMember);
}
