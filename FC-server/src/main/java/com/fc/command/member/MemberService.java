package com.fc.command.member;

import com.fc.command.member.model.MemberCommand;
import com.fc.core.infra.Validator;

public interface MemberService {
	void create(Validator<MemberCommand.CreateMemberCommand> validator, MemberCommand.CreateMemberCommand command);
}
