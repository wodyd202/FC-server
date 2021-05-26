package com.fc.core.snapshot;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import com.fc.core.domain.AggregateRoot;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@SuppressWarnings({ "serial", "rawtypes" })
@MappedSuperclass
@NoArgsConstructor
public class Snapshot<A extends AggregateRoot, ID> implements Serializable {

	@Id
	protected ID identifier;

	protected Long version;

	@Lob
	protected A aggregateRoot;

	public Snapshot(ID identifier, Long version, A aggregateRoot) {
		this.identifier = identifier;
		this.version = version;
		this.aggregateRoot = aggregateRoot;
	}
}
