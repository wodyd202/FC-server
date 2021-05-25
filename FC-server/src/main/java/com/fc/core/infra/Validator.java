package com.fc.core.infra;

public interface Validator<T> {
	void validation(T target);
}
