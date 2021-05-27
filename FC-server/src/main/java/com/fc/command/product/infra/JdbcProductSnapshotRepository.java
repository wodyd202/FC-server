package com.fc.command.product.infra;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.fc.core.snapshot.Snapshot;
import com.fc.core.snapshot.SnapshotRepository;
import com.fc.domain.product.Product;
import com.fc.domain.product.ProductId;
import com.fc.domain.product.snapshot.ProductSnapshot;

public class JdbcProductSnapshotRepository implements SnapshotRepository<Product, ProductId>{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Snapshot<Product, ProductId>> findLatest(ProductId id) {
		return Optional.ofNullable(em.find(ProductSnapshot.class, id));
	}

	@Override
	@Transactional
	public void save(Snapshot<Product, ProductId> snapshot) {
		ProductSnapshot findSnapshot = em.find(ProductSnapshot.class, snapshot.getIdentifier());
		if(findSnapshot == null) {
			findSnapshot = new ProductSnapshot(snapshot.getIdentifier(), snapshot.getVersion(), snapshot.getAggregateRoot());
			em.persist(findSnapshot);
		}else {
			findSnapshot.change(snapshot);
		}
	}

}
