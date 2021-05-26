package com.fc.core.domain;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fc.core.event.Event;
import com.fc.core.exception.EventApplyException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@JsonIgnoreProperties({ "identifier", "expectedVersion", "uncommittedChanges" })
@SuppressWarnings({"serial","rawtypes"})
public abstract class AggregateRoot<ID> implements Serializable {

	private ID identifier;

	private Long expectedVersion = 0L;

	private List<Event> changeEvents = new ArrayList<>();

	public AggregateRoot(ID identifier) {
		this.identifier = identifier;
	}

	public AggregateRoot() {

	}

	public void markChangesAsCommitted() {
		this.changeEvents.clear();
	}

	public List<Event> getUncommittedChanges() {
		return this.changeEvents;
	}

	public ID getIdentifier() {
		return identifier;
	}

	public Long getExpectedVersion() {
		return expectedVersion;
	}

	public void replay(List<Event> changes) {
		for (Event event : changes) {
			applyChange(event, false);
			this.expectedVersion++;
		}
	}

	protected void applyChange(Event change) {
		applyChange(change, true);
	}

	private static final String APPLY_METHOD_NAME = "apply";
	private void applyChange(Event event, boolean isNew) {
		Method method;
		try {
			method = this.getClass().getDeclaredMethod(APPLY_METHOD_NAME, event.getClass());
			if (method != null) {
				method.setAccessible(true);
				method.invoke(this, event);
			}

			if (isNew) {
				changeEvents.add(event);
			}
		} catch (IllegalAccessException | IllegalArgumentException
			| InvocationTargetException | NoSuchMethodException e) {
			log.error(e.getMessage(), e);
			throw new EventApplyException(e.getMessage(), e);
		}
	}
}
