package com.fc.query.member.infra;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.fc.domain.member.Email;
import com.fc.domain.member.read.Member;
import com.fc.domain.member.read.QMember;
import com.fc.query.member.model.MemberQuery.Address;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class JdbcMemberRepository implements MemberRepository{

	@PersistenceContext
	private EntityManager em;
	
	private QMember member = QMember.member;
	
	@Override
	public Optional<Member> findByEmail(Email email) {
		JPAQuery<Member> query = new JPAQuery<>(em);
		return Optional.ofNullable(
				query
				.select(Projections.constructor(Member.class, 
						member.email,
						member.password()
					))
				.from(member)
				.where(member.email.eq(email)).fetchOne());
	}

	@Override
	public boolean existByEmail(Email email) {
		JPAQuery<Member> query = new JPAQuery<>(em);
		return query.from(member).where(member.email.eq(email)).fetchCount() == 0 ? false : true;
	}

	@Override
	public Optional<Address> getAddressOfMember(Email email) {
		JPAQuery<Member> query = new JPAQuery<>(em);
		return Optional.ofNullable(
				query
				.select(Projections.constructor(Address.class, 
						member.address().longtitude,
						member.address().letitude,
						member.address().province,
						member.address().city,
						member.address().neighborhood
					))
				.from(member)
				.where(member.email.eq(email))
				.fetchOne()
			);
	}

}
