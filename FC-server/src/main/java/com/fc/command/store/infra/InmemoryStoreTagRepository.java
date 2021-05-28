package com.fc.command.store.infra;

import java.util.ArrayList;
import java.util.List;

import com.fc.domain.store.StoreTag;

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
