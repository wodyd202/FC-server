package com.fc.command.store.infra;

import java.util.ArrayList;
import java.util.List;

import com.fc.domain.store.StoreStyle;

/**
  * @Date : 2021. 5. 27. 
  * @작성자 : LJY
  * @프로그램 설명 : 업체 스타일 repository
  */
public class InmemoryStoreStyleRepository implements StoreStyleRepository {
	private List<StoreStyle> repo = new ArrayList<>();
	
	@Override
	public boolean existByStyleName(String value) {
		return repo.contains(new StoreStyle(value));
	}

	@Override
	public void save(StoreStyle style) {
		repo.add(style);
	}

	@Override
	public List<StoreStyle> findAll() {
		return repo;
	}

}
