package com.fc.query.member.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fc.domain.member.Email;
import com.fc.domain.member.read.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Email>{

}
