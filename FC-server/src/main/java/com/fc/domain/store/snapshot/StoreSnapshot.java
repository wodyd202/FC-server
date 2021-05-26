package com.fc.domain.store.snapshot;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fc.core.snapshot.Snapshot;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Store;

import lombok.Getter;

@Entity
@Getter
@Table(name = "STORE_SNAPSHOT")
public class StoreSnapshot extends Snapshot<Store, Owner>{
	private static final long serialVersionUID = 1L;

	@Override
	@Convert(converter = StoreAggregateConverter.class)
	public Store getAggregateRoot() {
		return super.getAggregateRoot();
	}
	
	StoreSnapshot() {}
	
	public StoreSnapshot(Owner identifier, Long version, Store aggregateRoot) {
		super(identifier, version, aggregateRoot);
	}

	public void change(Snapshot<Store, Owner> snapshot) {
		this.aggregateRoot = snapshot.getAggregateRoot();
		this.version = snapshot.getVersion();
	}
}
