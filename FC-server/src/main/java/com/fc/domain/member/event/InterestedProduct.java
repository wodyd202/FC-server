package com.fc.domain.member.event;

import com.fc.domain.member.Email;
import com.fc.domain.member.StoreProductId;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterestedProduct extends AbstractMemberEvent {
	private static final long serialVersionUID = 1L;
	private StoreProductId productId;
	
	public InterestedProduct(Email email, StoreProductId productId) {
		this.email = email;
		this.productId = productId;
	}
}
