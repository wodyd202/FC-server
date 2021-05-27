package com.fc.command.store.infra;

public interface StoreStyleRepository {
	boolean existByStyleName(String styleName);
}
