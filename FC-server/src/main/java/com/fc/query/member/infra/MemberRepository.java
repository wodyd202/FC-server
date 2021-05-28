package com.fc.query.member.infra;

import java.util.Optional;

import com.fc.domain.member.Address;
import com.fc.domain.member.Email;
import com.fc.domain.member.read.Member;

public interface MemberRepository {
	Optional<Member> findByEmail(Email email);

	boolean existByEmail(Email email);

	Optional<Address> getAddressOfMember(Email email);
}
