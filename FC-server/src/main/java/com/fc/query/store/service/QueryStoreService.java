package com.fc.query.store.service;

import org.springframework.stereotype.Service;

import com.fc.command.store.exception.StoreNotFoundException;
import com.fc.domain.member.read.Member;
import com.fc.domain.store.Owner;
import com.fc.query.store.infra.StoreRepository;
import com.fc.query.store.model.StoreQuery;
import com.fc.query.store.model.StoreSearch;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QueryStoreService {
	private StoreRepository storeRepository;

	public StoreQuery.StoreMainInfo findByOwner(Owner owner, Member loginMember) {
		return storeRepository.findByOwner(owner, loginMember).orElseThrow(() -> new StoreNotFoundException("해당 업체 정보가 존재하지 않습니다."));
	}

	public StoreQuery.StoreList findAll(StoreSearch dto, Member loginMember) {
		return storeRepository.findAll(dto, loginMember);
	}
}
