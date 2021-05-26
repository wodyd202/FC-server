package com.fc.command.member.infra;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.fc.domain.member.Email;
import com.fc.domain.member.event.MemberRawEvent;
import com.fc.domain.member.event.QMemberRawEvent;
import com.querydsl.jpa.impl.JPAQuery;

public class JdbcMemberEventRepository implements MemberEventStoreRepository{

	@PersistenceContext
	private EntityManager em;
	
	private QMemberRawEvent memberRowEvent = QMemberRawEvent.memberRawEvent;
	
	@Override
	@Transactional(readOnly = true)
	public long countByEmail(Email identifier) {
		JPAQuery<MemberRawEvent> query = new JPAQuery<>(em);
		return query.from(memberRowEvent).where(memberRowEvent.identifiier.eq(identifier)).fetchCount();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MemberRawEvent> findByEmail(Email identifier) {
		JPAQuery<MemberRawEvent> query = new JPAQuery<>(em);
		return query.from(memberRowEvent).where(memberRowEvent.identifiier.eq(identifier)).fetch();
	}

	@Override
	@Transactional(readOnly = true)
	public List<MemberRawEvent> findByEmailAndVersion(Email identifier, Long version) {
		JPAQuery<MemberRawEvent> query = new JPAQuery<>(em);
		return query.from(memberRowEvent)
				.where(memberRowEvent.identifiier.eq(identifier)
						.and(memberRowEvent.version.gt(version - 1))).fetch();
	}

	@Override
	@Transactional
	public void save(MemberRawEvent event) {
		em.persist(event);
	}

}
