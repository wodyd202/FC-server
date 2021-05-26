package com.fc.core.event;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public interface Event<ID> extends Serializable {
	@JsonIgnore
	ID getIdentifier();
}
