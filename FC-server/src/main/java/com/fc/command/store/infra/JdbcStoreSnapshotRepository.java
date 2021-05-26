package com.fc.command.store.infra;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.fc.core.snapshot.Snapshot;
import com.fc.core.snapshot.SnapshotRepository;
import com.fc.domain.store.Owner;
import com.fc.domain.store.Store;
import com.fc.domain.store.snapshot.StoreSnapshot;

public class JdbcStoreSnapshotRepository implements SnapshotRepository<Store, Owner>{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Snapshot<Store, Owner>> findLatest(Owner id) {
		return Optional.ofNullable(em.find(StoreSnapshot.class, id));
	}

	@Override
	@Transactional
	public void save(Snapshot<Store, Owner> snapshot) {
		StoreSnapshot findSnapshot = em.find(StoreSnapshot.class, snapshot.getIdentifier());
		if(findSnapshot == null) {
			findSnapshot = new StoreSnapshot(snapshot.getIdentifier(), snapshot.getVersion(), snapshot.getAggregateRoot());
			em.persist(findSnapshot);
		}else {
			findSnapshot.change(snapshot);
		}
	}

}
