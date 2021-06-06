package com.fc.command.store.infra;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fc.domain.store.StoreTag;

@Repository
public class InmemoryStoreTagRepository implements StoreTagRepository {
	private List<StoreTag> repo = new ArrayList<>();

	@Override
	public boolean existByTagName(String tagName) {
		return repo.contains(new StoreTag(tagName));
	}

	@Override
	public void save(StoreTag tag) {
		repo.add(tag);
	}

	@Override
	public List<StoreTag> findAll() {
		return repo;
	}

}
