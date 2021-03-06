package com.fc.command.member;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fc.command.common.address.infra.AddressDetailGetter;
import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.member.exception.AlreadyDeletedMemberException;
import com.fc.command.member.exception.AlreadyExistMemberException;
import com.fc.command.member.exception.InvalidMemberException;
import com.fc.command.member.exception.MemberNotFoundException;
import com.fc.command.member.infra.MemberEventHandler;
import com.fc.command.member.model.MemberCommand.ChangeAddress;
import com.fc.command.member.model.MemberCommand.ChangePassword;
import com.fc.command.member.model.MemberCommand.CreateMember;
import com.fc.command.product.exception.ProductNotFoundException;
import com.fc.command.store.exception.StoreNotFoundException;
import com.fc.core.infra.Validator;
import com.fc.domain.member.Address;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;
import com.fc.domain.member.Password;
import com.fc.domain.member.StoreOwner;
import com.fc.domain.member.StoreProductId;
import com.fc.domain.product.ProductId;
import com.fc.query.product.infra.ProductRepository;
import com.fc.query.store.infra.StoreRepository;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@Service
@AllArgsConstructor
public class SimpleMemberService implements MemberService {
	private MemberEventHandler memberEventHandler;
	private PasswordEncoder passwordEncoder;
	
	private StoreRepository storeRepository;
	private ProductRepository productRepository;
	
	@Override
	public Member create(
			Validator<CreateMember> validator,
			CreateMember command
		) {
		validator.validation(command);
		Optional<Member> findMember = memberEventHandler.find(new Email(command.getEmail()));
		if(findMember.isPresent()) {
			if(findMember.get().isDelete()) {
				throw new AlreadyDeletedMemberException("해당 이메일로 가입한 회원이 이미 존재합니다.");
			}else {
				throw new AlreadyExistMemberException("해당 이메일로 가입한 회원이 이미 존재합니다.");
			}
		}
		Member member = Member.create(passwordEncoder, command);
		memberEventHandler.save(member);
		return member;
	}

	@Override
	public Member changeAddress(
			Validator<AddressCommand> validator, 
			AddressDetailGetter getter,
			Email targetUserEmail, 
			ChangeAddress command
		) {
		AddressCommand address = AddressCommand.of(command);
		validator.validation(address);
		Address detailAddress = getter.getDetail(address);
		Member findMember = memberEventHandler.find(targetUserEmail).orElseThrow(()->new MemberNotFoundException("해당 이메일의 회원이 존재하지 않습니다."));
		findMember.changeAddress(detailAddress);
		memberEventHandler.save(findMember);
		return findMember;
	}

	@Override
	public Member changePassword(
			Validator<ChangePassword> validator, 
			Email targetUserEmail, 
			ChangePassword command
		) {
		validator.validation(command);
		Member findMember = memberEventHandler.find(targetUserEmail).orElseThrow(()->new MemberNotFoundException("해당 이메일의 회원이 존재하지 않습니다."));
		
		verifyEqualPasswordWithChangePassword(command, findMember);
		verifyEqualPasswordWithOriginPassword(command, findMember);
		
		findMember.changePassword(new Password(command.getChangePassword()));
		memberEventHandler.save(findMember);
		return findMember;
	}

	private void verifyEqualPasswordWithChangePassword(ChangePassword command, Member findMember) {
		if(passwordEncoder.matches(command.getChangePassword(), findMember.getPassword().getValue())) {
			throw new InvalidMemberException("변경하고자 하는 비밀번호가 기존 비밀번호와 동일합니다.");
		}
	}

	private void verifyEqualPasswordWithOriginPassword(ChangePassword command, Member findMember) {
		if(!passwordEncoder.matches(command.getOriginPassword(), findMember.getPassword().getValue())) {
			throw new InvalidMemberException("기존 비밀번호가 일치하지 않습니다.");
		}
	}

	@Override
	public void interestStore(
			Email me, 
			StoreOwner targetStoreOwner
		) {
		if(!storeRepository.existByOnwer(new com.fc.domain.store.Owner(targetStoreOwner.getEmail()))) {
			throw new StoreNotFoundException("해당 업체 정보가 존재하지 않습니다.");
		}
		Member findMember = memberEventHandler.find(me).orElseThrow(()->new MemberNotFoundException("해당 이메일의 회원이 존재하지 않습니다."));
		if(findMember.isAlreadyInterestStore(targetStoreOwner)) {
			findMember.removeInterestStore(targetStoreOwner);
		}else {
			findMember.interestStore(targetStoreOwner);
		}
		memberEventHandler.save(findMember);
	}

	@Override
	public void interestProduct(
			Email me, 
			StoreProductId productId
		) {
		if(!productRepository.existByProductId(new ProductId(productId.getId()))) {
			throw new ProductNotFoundException("해당 의류 정보가 존재하지 않습니다.");
		}
		Member findMember = memberEventHandler.find(me).orElseThrow(()->new MemberNotFoundException("해당 이메일의 회원이 존재하지 않습니다."));
		if(findMember.isAlreadyInterestProduct(productId)) {
			findMember.removeInterestProduct(productId);
		}else {
			findMember.interestProduct(productId);
		}
		memberEventHandler.save(findMember);
	}
	
}
