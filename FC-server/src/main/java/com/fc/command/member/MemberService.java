package com.fc.command.member;

import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.member.model.MemberCommand;
import com.fc.command.member.model.MemberCommand.ChangeAddress;
import com.fc.command.member.model.MemberCommand.ChangePassword;
import com.fc.core.infra.Validator;
import com.fc.domain.member.Email;

public interface MemberService {
	void create(Validator<MemberCommand.CreateMember> validator, MemberCommand.CreateMember command);

	void changeAddress(Validator<AddressCommand> validator, AddressDetailGetter getter, Email to, ChangeAddress command);

	void changePassword(Validator<ChangePassword> validator, Email to, ChangePassword command);
}
