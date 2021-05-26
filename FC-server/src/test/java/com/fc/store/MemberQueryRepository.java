package com.fc.store;

import java.util.Optional;

import com.fc.domain.member.Email;
import com.fc.domain.member.read.Member;

public interface MemberQueryRepository {
	Optional<Member> findByEmail(Email email);
}
