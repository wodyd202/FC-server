package com.fc.query.store.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fc.command.store.exception.StoreNotFoundException;
import com.fc.domain.store.Owner;
import com.fc.domain.store.read.Store;
import com.fc.query.store.infra.StoreRepository;
import com.fc.query.store.model.StoreQuery;
import com.fc.query.store.model.StoreSearch;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QueryStoreService {
	private StoreRepository storeRepository;

	public Store findByOwner(Owner owner) {
		return storeRepository.findByOwner(owner).orElseThrow(() -> new StoreNotFoundException("해당 업체 정보가 존재하지 않습니다."));
	}

	public List<StoreQuery.StoreList> findAll(StoreSearch dto) {
		
		return storeRepository.findAll(dto);
	}
}
