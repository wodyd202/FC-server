package com.fc.service.member;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fc.core.infra.Validator;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;
import com.fc.service.member.exception.AlreadyDeletedMemberException;
import com.fc.service.member.exception.AlreadyExistMemberException;
import com.fc.service.member.infra.MemberEventHandler;
import com.fc.service.member.model.MemberCommand.CreateMemberCommand;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@Service
public class SimpleMemberService implements MemberService {
	private MemberEventHandler memberEventHandler;
	
	@Override
	public void create(Validator<CreateMemberCommand> validator, CreateMemberCommand command) {
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

}
