package com.fc.command.product.infra;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.fc.domain.product.ProductId;
import com.fc.domain.product.event.ProductRawEvent;
import com.fc.domain.product.event.QProductRawEvent;
import com.querydsl.jpa.impl.JPAQuery;

public class JdbcProductEventRepository implements ProductEventStoreRepository{

	@PersistenceContext
	private EntityManager em;
	
	private QProductRawEvent productRawEvent = QProductRawEvent.productRawEvent;
	
	@Override
	@Transactional(readOnly = true)
	public long countByProductId(ProductId identifier) {
		JPAQuery<ProductRawEvent> query = new JPAQuery<>(em);
		return query.from(productRawEvent).where(productRawEvent.identifiier.eq(identifier)).fetchCount();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ProductRawEvent> findByProductId(ProductId identifier) {
		JPAQuery<ProductRawEvent> query = new JPAQuery<>(em);
		return query.from(productRawEvent).where(productRawEvent.identifiier.eq(identifier)).fetch();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductRawEvent> findByProductIdAndVersion(ProductId identifier, Long version) {
		JPAQuery<ProductRawEvent> query = new JPAQuery<>(em);
		return query.from(productRawEvent)
				.where(productRawEvent.identifiier.eq(identifier)
						.and(productRawEvent.version.gt(version - 1))).fetch();
	}

	@Override
	@Transactional
	public void save(ProductRawEvent event) {
		em.persist(event);
	}

}
