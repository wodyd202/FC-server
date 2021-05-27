package com.fc.command.store.infra;

public interface StoreTagRepository {
	boolean existByTagName(String tagName);
}
