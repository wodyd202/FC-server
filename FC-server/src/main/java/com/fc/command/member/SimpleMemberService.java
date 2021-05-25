package com.fc.command.member;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.member.exception.AlreadyDeletedMemberException;
import com.fc.command.member.exception.AlreadyExistMemberException;
import com.fc.command.member.exception.MemberNotFoundException;
import com.fc.command.member.infra.MemberEventHandler;
import com.fc.command.member.model.MemberCommand.ChangeAddress;
import com.fc.command.member.model.MemberCommand.CreateMember;
import com.fc.core.infra.Validator;
import com.fc.domain.member.Address;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@Service
public class SimpleMemberService implements MemberService {
	private MemberEventHandler memberEventHandler;
	
	@Override
	public void create(
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
		Member member = Member.create(command);
		memberEventHandler.save(member);
	}

	@Override
	public void changeAddress(
			Validator<AddressCommand> validator, 
			AddressDetailGetter getter,
			Email to, 
			ChangeAddress command
		) {
		AddressCommand address = AddressCommand.of(command);
		validator.validation(address);
		Address detailAddress = getter.getDetail(address);
		Member findMember = memberEventHandler.find(to).orElseThrow(()->new MemberNotFoundException("해당 이메일의 회원이 존재하지 않습니다."));
		findMember.changeAddress(detailAddress);
		memberEventHandler.save(findMember);
	}

}
