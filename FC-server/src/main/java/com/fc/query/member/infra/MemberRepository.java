package com.fc.query.member.infra;

import java.util.Optional;

import com.fc.domain.member.Email;
import com.fc.domain.member.read.Member;
import com.fc.query.member.model.MemberQuery;
import com.fc.query.member.model.MemberQuery.Address;

public interface MemberRepository {
	Optional<Member> findByEmail(Email email);

	boolean existByEmail(Email email);

	Optional<Address> findAddressByEmail(Email email);
	
	MemberQuery.InterestStoreList findInterestStoreListByEmail(Email email);
	
	MemberQuery.InterestProductList findInterestProductListByEmail(Email email);
}
