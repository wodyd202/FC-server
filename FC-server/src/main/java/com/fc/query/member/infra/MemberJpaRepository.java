package com.fc.query.member.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.fc.domain.member.Email;
import com.fc.domain.member.read.Member;

@Transactional
public interface MemberJpaRepository extends JpaRepository<Member, Email>{
}
