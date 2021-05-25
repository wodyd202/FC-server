package com.fc.service.member;

import com.fc.core.infra.Validator;
import com.fc.service.member.model.MemberCommand;

public interface MemberService {
	void create(Validator<MemberCommand.CreateMemberCommand> validator, MemberCommand.CreateMemberCommand command);
}
