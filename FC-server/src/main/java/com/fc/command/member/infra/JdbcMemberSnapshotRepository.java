package com.fc.command.member.infra;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.fc.core.snapshot.Snapshot;
import com.fc.core.snapshot.SnapshotRepository;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;
import com.fc.domain.member.snapshot.MemberSnapshot;

public class JdbcMemberSnapshotRepository implements SnapshotRepository<Member, Email>{

	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Snapshot<Member, Email>> findLatest(Email id) {
		return Optional.ofNullable(em.find(MemberSnapshot.class, id));
	}

	@Override
	@Transactional
	public void save(Snapshot<Member, Email> snapshot) {
		MemberSnapshot findSnapshot = em.find(MemberSnapshot.class, snapshot.getIdentifier());
		if(findSnapshot == null) {
			findSnapshot = new MemberSnapshot(snapshot.getIdentifier(), snapshot.getVersion(), snapshot.getAggregateRoot());
			em.persist(findSnapshot);
		}else {
			findSnapshot.change(snapshot);
		}
	}

}
