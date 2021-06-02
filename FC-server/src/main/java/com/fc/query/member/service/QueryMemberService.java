package com.fc.query.member.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fc.command.member.exception.MemberNotFoundException;
import com.fc.config.security.MemberPrincipal;
import com.fc.domain.member.Email;
import com.fc.domain.member.read.Member;
import com.fc.query.member.exception.AddressOfMemberNotFoundException;
import com.fc.query.member.infra.MemberRepository;
import com.fc.query.member.model.MemberQuery.Address;
import com.fc.query.member.model.MemberQuery.InterestProductList;
import com.fc.query.member.model.MemberQuery.InterestStoreList;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@Service
@AllArgsConstructor
public class QueryMemberService implements UserDetailsService{
	private MemberRepository memberRepository;
	
	public boolean existByEmail(Email email) {
		return memberRepository.existByEmail(email);
	}

	public Address getAddressOfMember(Email email) {
		return memberRepository.findAddressByEmail(email)
					.orElseThrow(()->new AddressOfMemberNotFoundException("해당 회원의 주소가 등록되어있지 않습니다."));
	}
	
	public InterestStoreList getInterestStoreList(Email email) {
		return memberRepository.findInterestStoreListByEmail(email);
	}
	
	public InterestProductList getInterestProductList(Email email) {
		return memberRepository.findInterestProductListByEmail(email);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member findMember = memberRepository.findByEmail(new Email(username))
				.orElseThrow(()->new MemberNotFoundException("해당 회원의 사용자가 존재하지 않습니다."));
		return new MemberPrincipal(findMember);
	}

}
