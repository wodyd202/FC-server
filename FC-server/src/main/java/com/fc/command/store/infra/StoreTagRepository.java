package com.fc.command.store.infra;

import java.util.List;

import com.fc.domain.store.StoreTag;

public interface StoreTagRepository {
	void save(StoreTag tag);
	boolean existByTagName(String tagName);
	List<StoreTag> findAll();
}
