package com.fc.domain.product;

import com.fc.domain.member.read.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "email")
public class Owner {
	private String email;

	public boolean isMyProduct(Product product) {
		return product.getOwner().equals(this);
	}

	public static Owner withMember(Member member) {
		return new Owner(member.getEmail().getValue());
	}
}
