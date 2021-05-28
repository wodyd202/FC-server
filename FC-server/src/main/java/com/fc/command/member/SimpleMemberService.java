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
import com.fc.core.infra.Validator;
import com.fc.domain.member.Address;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;
import com.fc.domain.member.Password;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@Service
public class SimpleMemberService implements MemberService {
	private MemberEventHandler memberEventHandler;
	private PasswordEncoder passwordEncoder;
	
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
		if(findMember.getPassword().equals(new Password(command.getChangePassword()))) {
			throw new InvalidMemberException("변경하고자 하는 비밀번호가 기존 비밀번호와 동일합니다.");
		}
	}

	private void verifyEqualPasswordWithOriginPassword(ChangePassword command, Member findMember) {
		if(!findMember.getPassword().equals(new Password(command.getOriginPassword()))) {
			throw new InvalidMemberException("기존 비밀번호가 일치하지 않습니다.");
		}
	}

}
