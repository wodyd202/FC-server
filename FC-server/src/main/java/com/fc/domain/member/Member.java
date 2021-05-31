package com.fc.domain.member;

import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fc.command.member.model.MemberCommand.CreateMember;
import com.fc.core.domain.AggregateRoot;
import com.fc.domain.member.event.ChangedMemberAddress;
import com.fc.domain.member.event.ChangedMemberPassword;
import com.fc.domain.member.event.ConvertedToSeller;
import com.fc.domain.member.event.InterestedProduct;
import com.fc.domain.member.event.InterestedStore;
import com.fc.domain.member.event.RegisteredMember;
import com.fc.domain.member.event.RemovedInterestedProduct;
import com.fc.domain.member.event.RemovedInterestedStore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AggregateRoot<Email> {
	private static final long serialVersionUID = 1L;
	public enum MemberState { CREATE, DELETE }
	public enum MemberRule { BUYER, SELLER }
	
	Email email;
	Password password;
	Address address;
	MemberState state;
	MemberRule rule;
	Date createDateTime;

	InterestStores interestStores;
	InterestProducts interestProducts;
	
	@JsonIgnore
	public boolean isDelete() {
		return this.state == MemberState.DELETE;
	}
	
	Member(Email email){
		super(email);
		log.info("load member : " + email);
		this.email = email;
	}
	
	Member(String email,String password){
		super(new Email(email));
		this.email = getIdentifier();
		this.password = new Password(password);
		this.state = MemberState.CREATE;
		this.rule = MemberRule.BUYER;
		this.createDateTime = new Date();
		this.interestStores = new InterestStores();
		this.interestProducts = new InterestProducts();
		applyChange(new RegisteredMember(this.email,this.password,this.rule));
	}
	
	public static Member create(PasswordEncoder encoder, CreateMember command) {
		Member member = new Member(command.getEmail(), encoder.encode(command.getPassword()));
		return member;
	}
	
	public void changeAddress(Address address) {
		this.address = address;
		applyChange(new ChangedMemberAddress(this.email, this.address));
	}
	
	public void changePassword(Password password) {
		this.password = password;
		applyChange(new ChangedMemberPassword(this.email, this.password));
	}
	
	public void convertToSeller() {
		this.rule = MemberRule.SELLER;
		applyChange(new ConvertedToSeller(this.email));
	}
	
	public void interestStore(StoreOwner targetStoreOwner) {
		applyChange(new InterestedStore(this.email, targetStoreOwner));
	}
	
	public void removeInterestStore(StoreOwner targetStoreOwner) {
		applyChange(new RemovedInterestedStore(this.email, targetStoreOwner));
	}
	
	public boolean isAlreadyInterestStore(StoreOwner targetStoreOwner) {
		List<StoreOwner> stores = this.interestStores.getStores();
		for(StoreOwner store : stores) {
			if(store.getEmail().equals(targetStoreOwner.getEmail())) {
				return true;
			}
		}
		return false;
	}

	public void removeInterestProduct(StoreProductId productId) {
		this.interestProducts.add(productId);
	}

	public void interestProduct(StoreProductId productId) {
		this.interestProducts.remove(productId);
	}
	
	public boolean isAlreadyInterestProduct(StoreProductId productId) {
		List<StoreProductId> products = this.interestProducts.getProducts();
		for(StoreProductId product : products) {
			if(product.getId().equals(productId.getId())) {
				return true;
			}
		}
		return false;
	}

	protected void apply(RegisteredMember event) {
		this.email = event.getEmail();
		this.password = event.getPassword();
		this.state = MemberState.CREATE;
		this.rule = MemberRule.BUYER;
		this.createDateTime = new Date();
		this.interestStores = new InterestStores();
		this.interestProducts = new InterestProducts();
	}

	protected void apply(ChangedMemberAddress event) {
		this.address = event.getAddress();
	}
	
	protected void apply(ChangedMemberPassword event) {
		this.password = event.getPassword();
	}

	protected void apply(ConvertedToSeller event) {
		this.rule = MemberRule.SELLER;
	}
	
	protected void apply(InterestedStore event) {
		this.interestStores.add(event.getOwner());
	}
	
	protected void apply(RemovedInterestedStore event) {
		this.interestStores.remove(event.getOwner());
	}

	protected void apply(InterestedProduct event) {
		this.interestProducts.add(event.getProductId());
	}
	
	protected void apply(RemovedInterestedProduct event) {
		this.interestProducts.remove(event.getProductId());
	}

}
