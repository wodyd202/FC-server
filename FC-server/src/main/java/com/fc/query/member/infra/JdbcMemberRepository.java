package com.fc.query.member.infra;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.fc.domain.member.Email;
import com.fc.domain.member.read.Member;
import com.fc.domain.member.read.QMember;
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
						member.email
					))
				.from(member)
				.where(member.email.eq(email)).fetchOne());
	}

}
