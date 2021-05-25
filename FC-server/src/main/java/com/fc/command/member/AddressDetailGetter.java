package com.fc.command.member;

import com.fc.command.common.address.model.AddressCommand;
import com.fc.domain.member.Address;

public interface AddressDetailGetter {

	Address getDetail(AddressCommand command);
	
}
