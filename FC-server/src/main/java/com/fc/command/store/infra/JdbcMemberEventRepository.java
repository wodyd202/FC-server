package com.fc.command.store.infra;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.fc.domain.store.Owner;
import com.fc.domain.store.event.QStoreRawEvent;
import com.fc.domain.store.event.StoreRawEvent;
import com.querydsl.jpa.impl.JPAQuery;

public class JdbcMemberEventRepository implements StoreEventStoreRepository{

	@PersistenceContext
	private EntityManager em;
	
	private QStoreRawEvent StoreRawEvent = QStoreRawEvent.storeRawEvent;
	
	@Override
	@Transactional(readOnly = true)
	public long countByOwner(Owner identifier) {
		JPAQuery<StoreRawEvent> query = new JPAQuery<>(em);
		return query.from(StoreRawEvent).where(StoreRawEvent.identifiier.eq(identifier)).fetchCount();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<StoreRawEvent> findByOwner(Owner identifier) {
		JPAQuery<StoreRawEvent> query = new JPAQuery<>(em);
		return query.from(StoreRawEvent).where(StoreRawEvent.identifiier.eq(identifier)).fetch();
	}

	@Override
	@Transactional(readOnly = true)
	public List<StoreRawEvent> findByOwnerAndVersion(Owner identifier, Long version) {
		JPAQuery<StoreRawEvent> query = new JPAQuery<>(em);
		return query.from(StoreRawEvent)
				.where(StoreRawEvent.identifiier.eq(identifier)
						.and(StoreRawEvent.version.gt(version - 1))).fetch();
	}

	@Override
	@Transactional
	public void save(StoreRawEvent event) {
		em.persist(event);
	}

}
