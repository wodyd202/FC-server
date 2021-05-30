package com.fc.storeInterest;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.fc.command.member.MemberService;
import com.fc.domain.member.Email;
import com.fc.domain.store.Owner;

public class StoreInterestTest {
	
	MemberService memberService = mock(MemberService.class);
	
	@Test
	void test() {
		Email member = new Email("test@naver.com");
		Owner targetStoreOwner = new Owner("owner@naver.com");
		
		memberService.interestStore(member, targetStoreOwner);
	}
}
