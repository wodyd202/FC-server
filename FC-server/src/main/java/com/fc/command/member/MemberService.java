package com.fc.command.member;

import com.fc.command.common.address.infra.AddressDetailGetter;
import com.fc.command.common.address.model.AddressCommand;
import com.fc.command.member.model.MemberCommand;
import com.fc.command.member.model.MemberCommand.ChangeAddress;
import com.fc.command.member.model.MemberCommand.ChangePassword;
import com.fc.core.infra.Validator;
import com.fc.domain.member.Email;
import com.fc.domain.member.Member;
import com.fc.domain.member.StoreOwner;
import com.fc.domain.member.StoreProductId;

public interface MemberService {
	Member create(Validator<MemberCommand.CreateMember> validator, MemberCommand.CreateMember command);

	Member changeAddress(Validator<AddressCommand> validator, AddressDetailGetter getter, Email to, ChangeAddress command);

	Member changePassword(Validator<ChangePassword> validator, Email to, ChangePassword command);

	void interestStore(Email me, StoreOwner targetStoreOwner);
	
	void interestProduct(Email me, StoreProductId productId);
}
