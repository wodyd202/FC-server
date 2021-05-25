package com.fc.core.snapshot;

import lombok.Getter;

import java.io.Serializable;

import com.fc.core.domain.AggregateRoot;

/**
 * Created by jaceshim on 2017. 3. 7..
 */
@Getter
@SuppressWarnings({ "serial", "rawtypes" })
public class Snapshot<A extends AggregateRoot, ID> implements Serializable {

	private ID identifier;

	private Long version;

	private A aggregateRoot;

	public Snapshot(ID identifier, Long version, A aggregateRoot) {
		this.identifier = identifier;
		this.version = version;
		this.aggregateRoot = aggregateRoot;
	}
}
