package com.fc.query.member.infra;

import java.util.Optional;

import com.fc.domain.member.Email;
import com.fc.domain.member.read.Member;

public interface MemberRepository {
	Optional<Member> findByEmail(Email email);
}
