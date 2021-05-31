package com.fc.storeInterest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.Mockito.any;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fc.command.member.MemberService;
import com.fc.command.member.SimpleMemberService;
import com.fc.command.member.infra.MemberEventHandler;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;
import com.fc.domain.member.StoreOwner;
import com.fc.query.product.infra.ProductRepository;
import com.fc.query.store.infra.StoreRepository;

public class StoreInterestTest {
	
	MemberEventHandler memberEventHandler = mock(MemberEventHandler.class);
	StoreRepository storeRepository = mock(StoreRepository.class);
	MemberService memberService = new SimpleMemberService(memberEventHandler, mock(PasswordEncoder.class), storeRepository, mock(ProductRepository.class));
	
	@Test
	void 업체에_관심도_주기() {
		when(memberEventHandler.find(any()))
			.thenReturn(Optional.of(mock(Member.class)));
		
		when(storeRepository.existByOnwer(any()))
			.thenReturn(true);
		
		Email me = new Email("test@naver.com");
		StoreOwner targetStoreOwner = new StoreOwner("owner@naver.com");
		memberService.interestStore(me, targetStoreOwner);
	}
}
