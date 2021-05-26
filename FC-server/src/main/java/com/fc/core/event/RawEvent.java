package com.fc.core.event;

public interface RawEvent<ID> {

	ID getIdentifier();

	String getType();

	Long getVersion();

	String getPayload();
}
