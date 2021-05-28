package com.fc.command.store.infra;

import java.util.List;

import com.fc.domain.store.StoreStyle;

public interface StoreStyleRepository {
	void save(StoreStyle style);
	boolean existByStyleName(String styleName);
	List<StoreStyle> findAll();
}
