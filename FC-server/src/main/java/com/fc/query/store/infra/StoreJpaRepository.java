package com.fc.query.store.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fc.domain.store.Owner;
import com.fc.domain.store.read.Store;

public interface StoreJpaRepository extends JpaRepository<Store, Owner>{

}
